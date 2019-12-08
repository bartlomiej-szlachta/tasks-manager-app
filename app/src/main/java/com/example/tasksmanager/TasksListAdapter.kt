package com.example.tasksmanager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TasksListAdapter(
    private val tasks: List<TaskEntity>,
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<TasksListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task, itemClickListener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var taskId: Int? = null
        private val taskTitle: TextView = itemView.findViewById(R.id.task_title)
        private val taskStatus: TextView = itemView.findViewById(R.id.task_status)
        private val taskPriority: TextView = itemView.findViewById(R.id.task_priority)
        private val taskValidFrom: TextView = itemView.findViewById(R.id.task_valid_from)

        fun bind(task: TaskEntity, clickListener: OnItemClickListener) {
            taskId = task.id
            taskTitle.text = task.title
            taskStatus.text = task.status
            taskPriority.text = task.priority
            taskValidFrom.text = task.validFrom

            itemView.setOnClickListener {
                clickListener.onItemClicked(task)
            }
        }
    }
}