package com.example.todoapp.view.toDoList

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.todoapp.R
import com.example.todoapp.domain.TaskListDomain


class ToDoListAdapter (
    private val getTaskList: MutableList<TaskListDomain>
) : RecyclerView.Adapter<ToDoListAdapter.TaskRowPersonalOrWorkViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskRowPersonalOrWorkViewHolder {
        val transactionView = LayoutInflater.from(parent.context)
            .inflate(R.layout.to_do_task_item, parent, false)
        return TaskRowPersonalOrWorkViewHolder(transactionView)
    }

    override fun getItemCount(): Int {
        return getTaskList.size
    }

    override fun onBindViewHolder(holder: TaskRowPersonalOrWorkViewHolder, position: Int) {
        val currentItem = getTaskList[position]
        holder.taskRowText.text = currentItem.task
        holder.dateToComplete.text = currentItem.date

        val planetPersonalOrWork = if (currentItem.personalOrWork == "work") R.raw.mars else R.raw.planet
        holder.planetPersonalOrWorkItem.setAnimation(planetPersonalOrWork)
        holder.planetPersonalOrWorkItem.playAnimation()

        Log.d("ToDoListAdapter", "Task: ${currentItem.task}, Date: ${currentItem.date}, PersonalOrWork: ${currentItem.personalOrWork}")
    }

    fun deleteItem(position: Int) {
        getTaskList.removeAt(position)
        notifyItemRemoved(position)
    }

    class TaskRowPersonalOrWorkViewHolder(itemViewTask: View) :
        RecyclerView.ViewHolder(itemViewTask) {
        val taskRowText: TextView = itemView.findViewById(R.id.taskNameItem)
        val dateToComplete: TextView = itemView.findViewById(R.id.taskDateItem)
        val planetPersonalOrWorkItem: LottieAnimationView = itemView.findViewById(R.id.personalOrWorkPlanetItem)
    }

}
