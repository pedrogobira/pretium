package com.pedrogobira.pretium.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.pedrogobira.pretium.databinding.FragmentPresentBinding
import com.pedrogobira.pretium.service.constants.ServiceConstants
import com.pedrogobira.pretium.view.adapter.ServiceAdapter
import com.pedrogobira.pretium.view.listener.ServiceListener
import com.pedrogobira.pretium.viewmodel.ServicesViewModel

class PresentFragment : Fragment() {

    private var _binding: FragmentPresentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ServicesViewModel
    private val adapter = ServiceAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {

        viewModel = ViewModelProvider(this).get(ServicesViewModel::class.java)
        _binding = FragmentPresentBinding.inflate(inflater, container, false)

        binding.recyclerPresents.layoutManager = LinearLayoutManager(context)
        binding.recyclerPresents.adapter = adapter

        // Listener
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
                //viewModel.getPresent()
            }
        }

        // Cria os observadores
        observe()

        adapter.attachListener(listener)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        //viewModel.getPresent()
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