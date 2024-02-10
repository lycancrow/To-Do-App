package com.example.todoapp.view.taskTemplate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityTaskTemplateBinding
import com.example.todoapp.view.taskTemplateDetails.TaskTemplateDetails

class TaskTemplate : AppCompatActivity() {


    private lateinit var binding: ActivityTaskTemplateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskTemplateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        binding.doExercise.setOnClickListener{
            val intent = Intent(this, TaskTemplateDetails::class.java)
            intent.putExtra("taskDetail","exercise")
            startActivity(intent)
        }
        binding.goToSleep.setOnClickListener{
            val intent = Intent(this, TaskTemplateDetails::class.java)
            intent.putExtra("taskDetail","sleep")
            startActivity(intent)
        }
        binding.breakTime.setOnClickListener{
            val intent = Intent(this, TaskTemplateDetails::class.java)
            intent.putExtra("taskDetail","break")
            startActivity(intent)
        }
        binding.takePills.setOnClickListener{
            val intent = Intent(this, TaskTemplateDetails::class.java)
            intent.putExtra("taskDetail","pills")
            startActivity(intent)
        }
        binding.doGroceries.setOnClickListener{
            val intent = Intent(this, TaskTemplateDetails::class.java)
            intent.putExtra("taskDetail","groceries")
            startActivity(intent)
        }

    }
}