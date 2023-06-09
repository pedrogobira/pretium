package com.pedrogobira.pretium.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pedrogobira.pretium.databinding.RowServiceBinding
import com.pedrogobira.pretium.service.model.ServiceModel
import com.pedrogobira.pretium.view.listener.ServiceListener
import com.pedrogobira.pretium.view.viewholder.ServiceViewHolder

class ServiceAdapter : RecyclerView.Adapter<ServiceViewHolder>() {

    private var serviceList: List<ServiceModel> = arrayListOf()
    private lateinit var listener: ServiceListener

    /**
     * Faz a criação do layout da linha
     * Faz a criação de várias linhas que vão mostrar cada um dos serviços
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val item = RowServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(item, listener)
    }

    /**
     * Tamanho da RecyclerView
     */
    override fun getItemCount(): Int {
        return serviceList.count()
    }

    /**
     * Para cada linha, este método é chamado
     * É responsável por atribuir os valores de cada item para uma linha específica
     */
    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(serviceList[position])
    }

    /**
     * Atualização da lista de serviços
     */
    fun updateServices(list: List<ServiceModel>) {
        serviceList = list
        notifyDataSetChanged()
    }

    /**
     * Eventos na listagem
     */
    fun attachListener(listener: ServiceListener) {
        this.listener = listener
    }

}