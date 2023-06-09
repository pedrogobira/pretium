package com.pedrogobira.pretium.view

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.pedrogobira.pretium.R
import com.pedrogobira.pretium.databinding.ActivityServiceFormBinding
import com.pedrogobira.pretium.service.constants.ServiceConstants
import com.pedrogobira.pretium.service.model.ServiceModel
import com.pedrogobira.pretium.viewmodel.ServiceFormViewModel
import java.text.ParseException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ServiceFormActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: ServiceFormViewModel
    private var serviceId: Int = 0
    private lateinit var binding: ActivityServiceFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(ServiceFormViewModel::class.java)

        // Eventos
        setListeners()

        // Cria observadores
        observe()

        // Carrega dados do usuário, caso haja
        loadData()
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.button_save) {
            val pricePerHourStr = binding.editPricePerHour.text.toString()
            val hoursStr = binding.editHours.text.toString()
            val minutesStr = binding.editMinutes.text.toString()
            val description = binding.editDescription.text.toString()
            val dateValue = binding.textDateValue.text.toString()

            if (pricePerHourStr.isEmpty() || hoursStr.isEmpty() || minutesStr.isEmpty() || description.isEmpty() || dateValue.isEmpty()) {
                Toast.makeText(this, "Todos os campos são necessários", Toast.LENGTH_SHORT).show()
                return
            }

            val pricePerHour = pricePerHourStr.toDoubleOrNull()
            val hours = hoursStr.toIntOrNull()
            val minutes = minutesStr.toIntOrNull()

            if (pricePerHour == null || hours == null || minutes == null) {
                Toast.makeText(this, "Informe dados válidos", Toast.LENGTH_SHORT).show()
                return
            }

            if (hours > 24 || minutes > 60) {
                Toast.makeText(this, "Horas ou minutos inválidos", Toast.LENGTH_SHORT).show()
                return
            }

            var date: LocalDate

            try {
                date = LocalDate.parse(dateValue, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            } catch (e: ParseException) {
                Toast.makeText(this, "Data ", Toast.LENGTH_SHORT).show()
                return
            }


            val serviceModel = ServiceModel(
                id = 0,
                pricePerHour = pricePerHour,
                hours = hours,
                minutes = minutes,
                day = date.dayOfMonth,
                month = date.monthValue,
                year = date.year,
                description = description
            )

            viewModel.save(serviceModel)
        }
        if (id == R.id.text_date_value) {
            binding.textDateValue.setTextColor(Color.argb(100, 98, 0, 238))
            showDatePickerDialog()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        var monthStr = (month + 1).toString()
        var dayStr = day.toString()

        if (monthStr.length == 1) {
            monthStr = "0$monthStr"
        }

        if (dayStr.length == 1) {
            dayStr = "0$dayStr"
        }

        binding.textDateValue.text = "$dayStr/$monthStr/$year"
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            serviceId = bundle.getInt(ServiceConstants.SERVICE.ID)
            viewModel.load(serviceId)
        }
    }

    private fun observe() {
        viewModel.save.observe(this, Observer {
            if (it) {
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Falha", Toast.LENGTH_SHORT).show()
            }
            finish()
        })

        viewModel.service.observe(this, Observer {
            binding.editHours.setText(it.hours.toString())
            binding.editMinutes.setText(it.minutes.toString())
            binding.editPricePerHour.setText(it.pricePerHour.toString())
            binding.textDateValue.text = "${it.day}/${it.month}/${it.year}"
            binding.editDescription.setText(it.description)
        })
    }

    private fun setListeners() {
        binding.buttonSave.setOnClickListener(this)
        binding.textDateValue.setOnClickListener(this)
    }

    private fun showDatePickerDialog() {
        val newFragment: DialogFragment = DatePickerFragment.newInstance(this)
        newFragment.show(supportFragmentManager, "datePicker")
    }
}
