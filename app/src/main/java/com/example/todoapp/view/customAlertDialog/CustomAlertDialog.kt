package com.example.todoapp.view.customAlertDialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.airbnb.lottie.LottieDrawable
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentCustomAlertDialogBinding


class CustomAlertDialog(
    private val failOrSuccess: String,
    private val valState: String
) : DialogFragment() {

    private var _binding: FragmentCustomAlertDialogBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding cannot be null.")




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCustomAlertDialogBinding.inflate(inflater, container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        customAlertConfiguration()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawableResource(R.drawable.alert_bg)

        return dialog
    }


    private fun customAlertConfiguration(){
        if(failOrSuccess == "fail"){
            binding?.alertAnimation?.setAnimation(R.raw.angry_alien)
            binding?.alertAnimation?.playAnimation()
            binding?.alertAnimation?.repeatCount = LottieDrawable.INFINITE
            binding?.alertSuccessOrFail?.text = "Ups..."
            binding?.alertMessage?.text = "It looks like you miss a date, or the task, please, check again."

        }else{
            binding?.alertAnimation?.setAnimation(R.raw.rocket)
            binding?.alertAnimation?.playAnimation()
            binding?.alertAnimation?.repeatCount = LottieDrawable.INFINITE
            binding?.alertSuccessOrFail?.text = "Done"
            binding?.alertMessage?.text = "Congratulations! your task now is scheduled"

        }


        binding?.btnOkay?.setOnClickListener {
            dismiss()
            if(valState == "closeActivity") {
                requireActivity().finish() // Cerrar la actividad
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Evita posibles fugas de memoria
    }

}