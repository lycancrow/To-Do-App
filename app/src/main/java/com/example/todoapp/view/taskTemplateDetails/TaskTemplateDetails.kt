package com.example.todoapp.view.taskTemplateDetails


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.lifecycle.ViewModelProvider
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todoapp.R
import com.example.todoapp.database.dbModel.ToDoDbModel
import com.example.todoapp.databinding.ActivityTaskTemplateDetailsBinding
import com.example.todoapp.view.customAlertDialog.CustomAlertDialog
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class TaskTemplateDetails() : AppCompatActivity() {

    private lateinit var binding: ActivityTaskTemplateDetailsBinding
    lateinit var  taskTemplateViewModel: TaskTemplateViewModel

    private var dateToLong: Long = 0
    private var dateToString: String = ""
    private var sun : Boolean = false
    private var mon : Boolean = false
    private var tue : Boolean = false
    private var wed : Boolean = false
    private var thu : Boolean = false
    private var fri : Boolean = false
    private var sat : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityTaskTemplateDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        taskTemplateViewModel = ViewModelProvider(this).get(TaskTemplateViewModel::class.java)


        binding.taskTemplateSun.setOnClickListener {
            sun = sun.not()
            if (sun){it.setBackgroundResource(R.drawable.selected_button_bg)}
            else{it.setBackgroundResource(R.drawable.week_button)}
        }
        binding.taskTemplateMon.setOnClickListener {
            mon = mon.not()
            if (mon){it.setBackgroundResource(R.drawable.selected_button_bg)}
            else{it.setBackgroundResource(R.drawable.week_button)}
        }
        binding.taskTemplateTue.setOnClickListener {
            tue = tue.not()
            if (tue){it.setBackgroundResource(R.drawable.selected_button_bg)}
            else{it.setBackgroundResource(R.drawable.week_button)}
        }
        binding.taskTemplateWed.setOnClickListener {
            wed = wed.not()
            if (wed){it.setBackgroundResource(R.drawable.selected_button_bg)}
            else{it.setBackgroundResource(R.drawable.week_button)}
        }
        binding.taskTemplateThu.setOnClickListener {
            thu = thu.not()
            if (thu){it.setBackgroundResource(R.drawable.selected_button_bg)}
            else{it.setBackgroundResource(R.drawable.week_button)}
        }
        binding.taskTemplateFri.setOnClickListener {
            fri = fri.not()
            if (fri){it.setBackgroundResource(R.drawable.selected_button_bg)}
            else{it.setBackgroundResource(R.drawable.week_button)}
        }
        binding.taskTemplateSat.setOnClickListener {
            sat = sat.not()
            if (sat){it.setBackgroundResource(R.drawable.selected_button_bg)}
            else{it.setBackgroundResource(R.drawable.week_button)}
        }


        optionSelected()

        binding.setHour.setOnClickListener {
            showDateTimePicker()
            //Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
        }


        binding.taskTemplateSubmit.setOnClickListener {
            addTaskToDatabase(sun,mon,tue,wed,thu,fri,sat,dateToLong,dateToString)

        }

    }


    fun optionSelected(){
        val task = intent.extras?.getString("taskDetail")
        binding.taskName.text = task

        if(task == "exercise"){
            binding.animationIcon.setAnimation(R.raw.exercise)
        }else if(task == "sleep"){
            binding.animationIcon.setAnimation(R.raw.moon)
        }else if(task == "break"){
            binding.animationIcon.setAnimation(R.raw.game_controller)
        }else if(task == "pills"){
            binding.animationIcon.setAnimation(R.raw.pill)
        }else if(task == "groceries"){
            binding.animationIcon.setAnimation(R.raw.groceries)
        }

    }



    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()

        // DatePickerDialog para seleccionar fecha
        val datePickerDialog = DatePickerDialog(
            this,
            R.style.DatePickerDialogTheme,
            { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"


                val timePickerDialog = TimePickerDialog(
                    this,
                    R.style.TimePickerDialogTheme,
                    { _, hourOfDay, minute ->
                        // Aquí obtienes la fecha y hora completa
                        val selectedDateTime = "$selectedDate $hourOfDay:$minute"
                        // Convertir la cadena a un objeto Date
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        val date = dateFormat.parse(selectedDateTime)
                        // Obtener la fecha y hora en milisegundos
                        val dateTimeInMillis = date?.time ?: 0L
                        dateToLong = dateTimeInMillis

                        dateToString = dateFormat.format(date ?: Date())

                        binding.setHour.text = dateToString

                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    false
                )

                timePickerDialog.show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }




    private fun addTaskToDatabase(
        sunday: Boolean,
        monday: Boolean,
        tuesday: Boolean,
        wednesday: Boolean,
        thursday: Boolean,
        friday: Boolean,
        saturday: Boolean,
        dateIssued: Long,
        dateToString: String
    )
    {
        val task = binding.reminderPhraseTemplate.text.toString()
        val isRepeated: Boolean =
            sunday || monday || tuesday || wednesday || thursday || friday || saturday


        if (task == "" || dateIssued == 0L) {
            customDialogFragment("fail")
        }
        else {
            taskTemplateViewModel.addTask(
                ToDoDbModel(
                    null,
                    task,
                    "personal",
                    sunday,
                    monday,
                    tuesday,
                    wednesday,
                    thursday,
                    friday,
                    saturday,
                    isRepeated,
                    dateIssued,
                    dateToString
                )
            )

            customDialogFragment("success")



        }

    }




    private fun customDialogFragment(state: String) {
        val customDialogFragment = CustomAlertDialog(state,"closeActivity")
        customDialogFragment.show(supportFragmentManager, "CustomDialogFragmentTag")
    }




}