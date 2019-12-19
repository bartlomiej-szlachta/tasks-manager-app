package com.example.tasksmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_task_details.*

class TaskDetailsFragment(private val dbHelper: DBHelper) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val taskId: Int = arguments?.getInt(TASK_ID_EXTRA)!!

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
        val task = dbHelper.getTaskById(taskId)
        task_details_title.text = task.title
        task_details_priority.text = task.priority
        when (task.status) {
            STATUS_NEW -> task_details_status.setSelection(0)
            STATUS_IN_PROGRESS -> task_details_status.setSelection(1)
            STATUS_SOLVED -> task_details_status.setSelection(2)
        }
        task_details_description.text = task.description
    }
}
