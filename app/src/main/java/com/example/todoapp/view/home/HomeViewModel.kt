package com.example.todoapp.view.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.ToDoDatabase
import com.example.todoapp.database.dbModel.ToDoDbModel
import com.example.todoapp.database.dbRepository.ToDoDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ToDoDbRepository
    var getAllTask: LiveData<List<ToDoDbModel>>

    init {
        val dao = ToDoDatabase.getDatabase(application).toDoDao()
        repository = ToDoDbRepository(dao)
        getAllTask = repository.getAllTasksLiveData()
    }

    fun getAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.refreshTasks() // Actualiza la lista
        }
    }


    fun deleteTask(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTasks(taskId)
        }
    }


}