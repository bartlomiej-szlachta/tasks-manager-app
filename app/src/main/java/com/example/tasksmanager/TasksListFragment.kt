package com.example.tasksmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_tasks_list.*

class TasksListFragment(
    private val dbHelper: DBHelper,
    private val itemClickListener: OnItemClickListener
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tasks_list.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        tasks_list.adapter = TasksListAdapter(dbHelper.getAllTasks(), itemClickListener)
    }

    override fun onResume() {
        super.onResume()
        tasks_list.adapter = TasksListAdapter(dbHelper.getAllTasks(), itemClickListener)
    }

}
