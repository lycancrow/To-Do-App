package com.example.todoapp.view.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R

class CalendarAdapter(private val daysOfMonth: ArrayList<String>, private val onItemListener: FragmentActivity?) :
    RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_item, parent, false)
        view.layoutParams.height = (parent.height * 0.166666666).toInt()
        return CalendarViewHolder(view, onItemListener)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayOfMonth.text = daysOfMonth[position]
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }

    inner class CalendarViewHolder(itemView: View, private val onItemListener: FragmentActivity?) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val dayOfMonth: TextView = itemView.findViewById(R.id.cellDayText)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onItemListener.apply {
                onItemListener
            }
        }

    }
}
