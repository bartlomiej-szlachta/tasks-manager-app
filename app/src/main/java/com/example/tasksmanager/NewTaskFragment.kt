package com.example.tasksmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_new_task.*

class NewTaskFragment(private val dbHelper: DBHelper) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        new_task_submit.setOnClickListener {
            val title = new_task_title.text.toString()
            val description = new_task_description.text.toString()

            if (title.length < 2) {
                Toast.makeText(context, MESSAGE_TITLE_REQUIRED, Toast.LENGTH_SHORT).show()
            } else {
                dbHelper.createTask(title, new_task_priority.selectedItem.toString(), description)
                Toast.makeText(context, MESSAGE_TASK_ADDED, Toast.LENGTH_SHORT).show()
                (context as MainActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        TasksListFragment(dbHelper, context as MainActivity)
                    )
                    .commit()
            }
        }
    }
}
