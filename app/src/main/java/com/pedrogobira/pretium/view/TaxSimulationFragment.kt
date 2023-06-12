package com.pedrogobira.pretium.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.pedrogobira.pretium.R
import com.pedrogobira.pretium.databinding.FragmentTaxSimulationBinding
import com.pedrogobira.pretium.service.constants.ServiceConstants
import com.pedrogobira.pretium.view.listener.ServiceListener
import com.pedrogobira.pretium.viewmodel.ServicesViewModel

class TaxSimulationFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentTaxSimulationBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ServicesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {

        viewModel = ViewModelProvider(this).get(ServicesViewModel::class.java)
        _binding = FragmentTaxSimulationBinding.inflate(inflater, container, false)

        val listener = object : ServiceListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, ServiceFormActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(ServiceConstants.SERVICE.ID, id)

                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                viewModel.delete(id)
                loadData()
            }
        }

        binding.buttonSearchServices.setOnClickListener(this)

        observe()

        return binding.root
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.button_search_services) {
            loadData()
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.totalRevenue.observe(viewLifecycleOwner) {
            binding.textGrossAmount.text = "Valor bruto: R$ ${String.format("%.2f", it)}"
        }
        viewModel.tax.observe(viewLifecycleOwner) {
            binding.textTax.text = "Tributação: ${it.descriptor} ${it.type} no valor de R\$ ${
                String.format(
                    "%.2f", it.value
                )
            }"
            binding.textProlabore.text = "Pró-labore: ${getProlabore()}"
            binding.textNetAmount.text = "Valor líquido: R$ ${
                String.format(
                    "%.2f", viewModel.totalRevenue.value!! - it.value - getProlabore()!!
                )
            }"
        }
    }

    private fun getMonth(): Int? {
        val monthStr = binding.editMonth.text.toString()

        if (monthStr.isEmpty()) {
            return null
        }

        val month = monthStr.toIntOrNull()

        if (month != null && month !in 1..12) {
            return null
        }

        return month
    }

    private fun getYear(): Int? {
        val yearStr = binding.editYear.text.toString()

        if (yearStr.isEmpty()) {
            return null
        }

        return yearStr.toIntOrNull()
    }

    private fun getProlabore(): Double? {
        val prolaboreStr = binding.editProlabore.text.toString()
        return prolaboreStr.toDoubleOrNull()
    }

    private fun clearPostGrossAmountData() {
        binding.textNetAmount.text = ""
        binding.textProlabore.text = ""
        binding.textTax.text = ""
    }

    private fun loadData() {
        val month = getMonth()
        val year = getYear()
        val prolabore = getProlabore()

        if (month == null || year == null || prolabore == null) {
            Snackbar.make(
                requireView(),
                "Todos os campos são necessários. Informe valores válidos",
                Snackbar.LENGTH_LONG
            ).show()
            return
        }

        viewModel.getAll(month, year)
        viewModel.calculateTotalPrice(month, year)

        if (viewModel.totalRevenue.value!! < prolabore) {
            Snackbar.make(
                requireView(), "Pró-labore maior que o valor bruto mensal", Snackbar.LENGTH_LONG
            ).show()
            clearPostGrossAmountData()
            return
        }

        viewModel.calculateTax(viewModel.totalRevenue.value!!, prolabore)
    }
}