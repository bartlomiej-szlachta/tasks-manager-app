package com.example.tasksmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_task_details.*

class TaskDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)
        title = TASK_ACTIVITY_TITLE_MODE_EDIT

        initializeFields()
    }

    private fun initializeFields() {
        val taskId = intent.getStringExtra(TASK_ID_EXTRA)
        val task = DBHelper(this).getTaskById(taskId!!.toInt())
        task_details_title.text = task.title
        task_details_priority.text = task.priority
        when(task.status) {
            "New" -> task_details_status.setSelection(0)
            "In progress" -> task_details_status.setSelection(1)
            "Solved" -> task_details_status.setSelection(2)
        }
        task_details_content.text = task.content
    }
}
