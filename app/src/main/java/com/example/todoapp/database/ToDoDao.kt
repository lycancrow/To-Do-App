package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.todoapp.database.dbModel.ToDoDbModel

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(insertTag: ToDoDbModel)

    @Query("SELECT * FROM to_do_tasks")
    fun getAllTasksLiveData(): LiveData<List<ToDoDbModel>>

    @Query("SELECT * FROM to_do_tasks WHERE personalOrWork=:personalOrWork")
    fun getWorkOrPersonalTasks(personalOrWork: String): List<ToDoDbModel>

    @Query("DELETE FROM to_do_tasks WHERE id=:taskId")
    suspend fun deleteTask(taskId: Int?)

    @Query("SELECT * FROM to_do_tasks")
    suspend fun refreshTasks(): List<ToDoDbModel>
}