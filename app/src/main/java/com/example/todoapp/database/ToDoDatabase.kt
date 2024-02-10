package com.example.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todoapp.database.dbModel.ToDoDbModel

@Database(entities = arrayOf(ToDoDbModel::class), version = 1, exportSchema = false)
abstract class ToDoDatabase:RoomDatabase(){
    abstract fun toDoDao(): ToDoDao
    companion object{
        @Volatile
        private var INSTANCE: ToDoDatabase? = null
        fun getDatabase(context:Context): ToDoDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoDatabase::class.java,
                    "to_do_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
