package com.example.todoapp.view.toDoList

import android.app.Application
import android.view.animation.Transformation
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.ToDoDatabase
import com.example.todoapp.database.dbModel.ToDoDbModel
import com.example.todoapp.database.dbRepository.ToDoDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ToDoListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ToDoDbRepository
    private val personalOrWorkFilter = MutableLiveData<String>()
    val getEspecificTasks: LiveData<List<ToDoDbModel>>

    init {
        val dao = ToDoDatabase.getDatabase(application).toDoDao()
        repository = ToDoDbRepository(dao)

        getEspecificTasks = MediatorLiveData<List<ToDoDbModel>>().apply {
            addSource(repository.getAllTasksLiveData()) { tasks ->
                val filteredTasks = tasks.filter { it.personalOrWork == personalOrWorkFilter.value }
                value = filteredTasks
            }

            addSource(personalOrWorkFilter) { filter ->
                value = repository.getAllTasksLiveData().value
            }
        }
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTasks(taskId)
        }
    }

    fun setPersonalOrWorkFilter(personalOrWork: String) {
        personalOrWorkFilter.value = personalOrWork
    }
}

