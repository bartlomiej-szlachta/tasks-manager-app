package com.example.tasksmanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_task.*

class NewTaskActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        title = TASK_ACTIVITY_TITLE_MODE_CREATE

        dbHelper = DBHelper(this)

        new_task_submit.setOnClickListener {

            // TODO: validate inputs

            dbHelper.createTask(
                new_task_title.text.toString(),
                new_task_priority.selectedItem.toString(),
                new_task_description.text.toString()
            )
            Toast.makeText(this, MESSAGE_TASK_ADDED, Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
