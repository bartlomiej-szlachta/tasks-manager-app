package com.example.tasksmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TaskActivity : AppCompatActivity() {

    private lateinit var mode : TaskActivityModeEnum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        mode = intent.getSerializableExtra(EXTRA_MODE) as TaskActivityModeEnum
        title = when (mode) {
            TaskActivityModeEnum.CREATE -> TASK_ACTIVITY_TITLE_MODE_CREATE
            TaskActivityModeEnum.EDIT -> TASK_ACTIVITY_TITLE_MODE_EDIT
        }
    }
}
