package com.example.todoapp.database.dbRepository

import androidx.lifecycle.LiveData
import com.example.todoapp.database.ToDoDao
import com.example.todoapp.database.dbModel.ToDoDbModel

class ToDoDbRepository(private val toDodDao: ToDoDao) {

    private val allTasksLiveData: LiveData<List<ToDoDbModel>> = toDodDao.getAllTasksLiveData()

    suspend fun insertTask(toDoDbModel: ToDoDbModel){
        toDodDao.insertTask(toDoDbModel)
    }

    fun getAllTasksLiveData(): LiveData<List<ToDoDbModel>> {
        return allTasksLiveData
    }

    suspend fun refreshTasks() {
        // Actualiza la lista en el hilo de fondo
        toDodDao.refreshTasks()
    }

    suspend fun getPersonalOrWorkTasks(personalOrWork:String): List<ToDoDbModel> {
        return toDodDao.getWorkOrPersonalTasks(personalOrWork)
    }

    suspend fun deleteTasks(id:Int){
        toDodDao.deleteTask(id)
    }
}
