package com.pedrogobira.pretium.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedrogobira.pretium.databinding.FragmentAllBinding
import com.pedrogobira.pretium.service.constants.ServiceConstants
import com.pedrogobira.pretium.view.adapter.ServiceAdapter
import com.pedrogobira.pretium.view.listener.ServiceListener
import com.pedrogobira.pretium.viewmodel.ServicesViewModel

class AllServicesFragment : Fragment() {

    private var _binding: FragmentAllBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ServicesViewModel
    private val adapter: ServiceAdapter = ServiceAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        viewModel = ViewModelProvider(this).get(ServicesViewModel::class.java)
        _binding = FragmentAllBinding.inflate(inflater, container, false)

        binding.recyclerAllServices.layoutManager = LinearLayoutManager(context)
        binding.recyclerAllServices.adapter = adapter

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
                viewModel.getAll()
            }
        }

        observe()

        adapter.attachListener(listener)

        viewModel.getAll()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.list.observe(viewLifecycleOwner) {
            adapter.updateServices(it)
        }
    }
}