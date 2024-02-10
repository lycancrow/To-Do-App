package com.example.todoapp.view.taskTemplateDetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.ToDoDatabase
import com.example.todoapp.database.dbModel.ToDoDbModel
import com.example.todoapp.database.dbRepository.ToDoDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskTemplateViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ToDoDbRepository
    init {
        val dao = ToDoDatabase.getDatabase(application).toDoDao()
        repository = ToDoDbRepository(dao)
    }
    fun addTask(toDoDbModel: ToDoDbModel) = viewModelScope.launch(Dispatchers.IO){
        repository.insertTask(toDoDbModel)
    }

}
