package com.example.todoapp.database.dbModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "to_do_tasks")
data class ToDoDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "task")
    val task: String,
    @ColumnInfo(name = "personalOrWork")
    val personalOrWork: String,
    @ColumnInfo(name = "sunday")
    val sunday : Boolean,
    @ColumnInfo(name = "monday")
    val monday : Boolean,
    @ColumnInfo(name = "tuesday")
    val tuesday : Boolean,
    @ColumnInfo(name = "wednesday")
    val wednesday : Boolean,
    @ColumnInfo(name = "thursday")
    val thursday : Boolean,
    @ColumnInfo(name = "friday")
    val friday : Boolean,
    @ColumnInfo(name = "saturday")
    val saturday : Boolean,
    @ColumnInfo(name = "isRepeated")
    val isRepeated: Boolean,
    @ColumnInfo(name = "date")
    val dateIssued: Long,
    @ColumnInfo(name = "dateToString")
    val dateToString: String
)


