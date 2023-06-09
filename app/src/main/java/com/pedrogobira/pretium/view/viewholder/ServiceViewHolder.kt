package com.pedrogobira.pretium.view.viewholder

import android.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.pedrogobira.pretium.R
import com.pedrogobira.pretium.databinding.RowServiceBinding
import com.pedrogobira.pretium.service.model.ServiceModel
import com.pedrogobira.pretium.view.listener.ServiceListener

class ServiceViewHolder(
    private val item: RowServiceBinding,
    private val listener: ServiceListener
) :
    RecyclerView.ViewHolder(item.root) {

    fun bind(service: ServiceModel) {
        var hoursStr = service.hours.toString()
        var minutesStr = service.minutes.toString()
        var dayStr = service.day.toString()
        var monthStr = service.month.toString()

        if (hoursStr.length == 1) {
            hoursStr = "0$hoursStr"
        }

        if (minutesStr.length == 1) {
            minutesStr = "0$minutesStr"
        }

        if (dayStr.length == 1) {
            dayStr = "0$dayStr"
        }

        if (monthStr.length == 1) {
            monthStr = "0$monthStr"
        }

        item.textPrice.text =
            String.format(
                "%.2f",
                ((service.hours.toDouble() + service.minutes.toDouble() / 60.0) * service.pricePerHour)
            )

        item.textHours.text = "${hoursStr}h${minutesStr}"
        item.textDate.text = "${dayStr}/${monthStr}/${service.year}"

        item.layoutRowServiceContainer.setOnClickListener {
            listener.onClick(service.id)
        }

        item.layoutRowServiceContainer.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.delete_service_confirmation_title)
                .setMessage(R.string.delete_service_confirmation_message)
                .setPositiveButton(R.string.delete) { dialog, which ->
                    listener.onDelete(service.id)
                }
                .setNeutralButton(R.string.cancel, null)
                .show()

            true
        }
    }
}