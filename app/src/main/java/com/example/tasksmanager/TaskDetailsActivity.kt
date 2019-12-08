package com.example.tasksmanager

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_task_details.*

class TaskDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)
        title = TASK_ACTIVITY_TITLE_MODE_EDIT

        val taskId = intent.getStringExtra(TASK_ID_EXTRA)!!.toInt()
        val dbHelper = DBHelper(this)

        task_details_status.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                dbHelper.changeStatus(taskId, parent.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        initializeFields(taskId)
    }

    private fun initializeFields(taskId: Int) {
        val task = DBHelper(this).getTaskById(taskId)
        task_details_title.text = task.title
        task_details_priority.text = task.priority
        when (task.status) {
            "New" -> task_details_status.setSelection(0)
            "In progress" -> task_details_status.setSelection(1)
            "Solved" -> task_details_status.setSelection(2)
        }
        task_details_content.text = task.content
    }
}
