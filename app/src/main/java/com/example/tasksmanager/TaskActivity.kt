package com.example.tasksmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TaskActivity : AppCompatActivity() {

    private lateinit var mode : TaskActivityMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        mode = intent.getSerializableExtra(EXTRA_MODE) as TaskActivityMode
        title = when (mode) {
            TaskActivityMode.CREATE -> TASK_ACTIVITY_TITLE_MODE_CREATE
            TaskActivityMode.EDIT -> TASK_ACTIVITY_TITLE_MODE_EDIT
        }
    }
}
