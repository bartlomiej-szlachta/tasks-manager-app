package com.example.tasksmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TasksListAdapter(private val tasks: List<TaskEntity>) :
    RecyclerView.Adapter<TasksListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
//        view.setOnClickListener { view.todo_id.setTextColor(Color.GREEN) }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = tasks[position]
        holder.todoTitle.text = todo.title
        holder.todoStatus.text = todo.status.value
        holder.todoPriority.text = todo.priority.value
        holder.todoValidFrom.text = todo.validFrom.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoTitle: TextView = itemView.findViewById(R.id.task_title)
        val todoStatus: TextView = itemView.findViewById(R.id.task_status)
        val todoPriority: TextView = itemView.findViewById(R.id.task_priority)
        val todoValidFrom: TextView = itemView.findViewById(R.id.task_valid_from)
    }
}