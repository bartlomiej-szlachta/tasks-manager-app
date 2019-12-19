package com.example.tasksmanager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = ACTIVITY_TITLE

        dbHelper = DBHelper(this)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, TasksListFragment(dbHelper, this))
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_task -> {
                supportFragmentManager
                    .beginTransaction()
                    .apply {
                        replace(R.id.fragment_container, NewTaskFragment(dbHelper))
                        addToBackStack(null)
                    }
                    .commit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClicked(task: TaskEntity) {
        val newFragment = TaskDetailsFragment(dbHelper)
        val args = Bundle()
        args.putInt(TASK_ID_EXTRA, task.id)
        newFragment.arguments = args
        supportFragmentManager
            .beginTransaction()
            .apply {
                replace(R.id.fragment_container, newFragment)
                addToBackStack(null)
            }
            .commit()
    }
}
