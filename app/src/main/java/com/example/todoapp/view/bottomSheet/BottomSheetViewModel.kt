package com.example.todoapp.view.bottomSheet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.ToDoDatabase
import com.example.todoapp.database.dbModel.ToDoDbModel
import com.example.todoapp.database.dbRepository.ToDoDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BottomSheetViewModel(application: Application) : AndroidViewModel(application) {

    internal var repository: ToDoDbRepository
    init {
        val dao = ToDoDatabase.getDatabase(application).toDoDao()
        repository = ToDoDbRepository(dao)
    }
    fun addTask(toDoDbModel: ToDoDbModel) = viewModelScope.launch(Dispatchers.IO){
        repository.insertTask(toDoDbModel)
    }

}

