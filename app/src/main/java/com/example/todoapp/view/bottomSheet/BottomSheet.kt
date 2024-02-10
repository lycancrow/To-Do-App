package com.example.todoapp.view.bottomSheet

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.R
import com.example.todoapp.database.dbModel.ToDoDbModel
import com.example.todoapp.databinding.FragmentBottomSheetBinding
import com.example.todoapp.view.customAlertDialog.CustomAlertDialog
import com.example.todoapp.view.taskTemplate.TaskTemplate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class BottomSheet(private val personalOrWorkOrGeneral: String?) :
    BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetBinding? = null
    lateinit var bottomSheetViewModel: BottomSheetViewModel
    private val binding get() = _binding!!
    private var personalOrWorkBool: Boolean = true


    private var dateToLong: Long = 0
    private var dateToString: String = ""
    private var sun : Boolean = false
    private var mon : Boolean = false
    private var tue : Boolean = false
    private var wed : Boolean = false
    private var thu : Boolean = false
    private var fri : Boolean = false
    private var sat : Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bottomSheetViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[BottomSheetViewModel::class.java]


        _binding = FragmentBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectDateHourDetails.setOnClickListener {
            showDateTimePicker()
        }

        binding.openTemplates.setOnClickListener {
            val intent = Intent(activity, TaskTemplate::class.java)
            startActivity(intent)
        }
        if (personalOrWorkOrGeneral == "personalOrWork") {
            binding.personalOrWork.visibility = View.GONE
        }
        else {
            binding.personalOrWork.setOnClickListener {
                personalOrWorkBool = !personalOrWorkBool
                personalOrWorkSelection()
            }
        }


        binding.bottomMenuSun.setOnClickListener {
            sun = sun.not()
            if (sun){it.setBackgroundResource(R.drawable.selected_button_bg)}
            else{it.setBackgroundResource(R.drawable.week_button)}
        }
        binding.bottomMenuMon.setOnClickListener {
            mon = mon.not()
            if (mon){it.setBackgroundResource(R.drawable.selected_button_bg)}
            else{it.setBackgroundResource(R.drawable.week_button)}
        }
        binding.bottomMenuTue.setOnClickListener {
            tue = tue.not()
            if (tue){it.setBackgroundResource(R.drawable.selected_button_bg)}
            else{it.setBackgroundResource(R.drawable.week_button)}
        }
        binding.bottomMenuWed.setOnClickListener {
            wed = wed.not()
            if (wed){it.setBackgroundResource(R.drawable.selected_button_bg)}
            else{it.setBackgroundResource(R.drawable.week_button)}
        }
        binding.bottomMenuThu.setOnClickListener {
            thu = thu.not()
            if (thu){it.setBackgroundResource(R.drawable.selected_button_bg)}
            else{it.setBackgroundResource(R.drawable.week_button)}
        }
        binding.bottomMenuFri.setOnClickListener {
            fri = fri.not()
            if (fri){it.setBackgroundResource(R.drawable.selected_button_bg)}
            else{it.setBackgroundResource(R.drawable.week_button)}
        }
        binding.bottomMenuSat.setOnClickListener {
            sat = sat.not()
            if (sat){it.setBackgroundResource(R.drawable.selected_button_bg)}
            else{it.setBackgroundResource(R.drawable.week_button)}
        }


        binding.submitTaskButton.setOnClickListener {
            addTaskToDatabase(sun,mon,tue,wed,thu,fri,sat,dateToLong,dateToString)
        }


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
        val task = binding.taskEditText.text.toString()
        val isRepeated: Boolean =
            sunday || monday || tuesday || wednesday || thursday || friday || saturday

        val personalOrBusiness: String = if (personalOrWorkOrGeneral != "general") {
            personalOrWorkOrGeneral.toString()
        } else {
            binding.personalOrWorkText.text.toString()
        }



        if (task == "" || dateIssued == 0L) {
            customDialogFragment("fail")
        }
        else {
            bottomSheetViewModel.addTask(
                ToDoDbModel(
                    null,
                    task,
                    personalOrBusiness,
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





    private fun customDialogFragment(state: String ){
        val customDialogFragment = CustomAlertDialog(state,"dontCloseActivity")
        customDialogFragment.show(requireActivity().supportFragmentManager, "CustomDialogFragmentTag")
    }

    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()

        // DatePickerDialog para seleccionar fecha
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DatePickerDialogTheme,
            { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"


                val timePickerDialog = TimePickerDialog(
                    requireContext(),
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

    private fun personalOrWorkSelection() {

        if (personalOrWorkBool == true) {
            binding.personalOrWorkPlanet.setAnimation(R.raw.planet)
            binding.personalOrWorkPlanet.playAnimation()
            binding.personalOrWorkText.text = "personal"
        } else {
            binding.personalOrWorkPlanet.setAnimation(R.raw.mars)
            binding.personalOrWorkPlanet.playAnimation()
            binding.personalOrWorkText.text = "work"
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}