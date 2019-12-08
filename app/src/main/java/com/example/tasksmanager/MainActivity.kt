package com.example.tasksmanager

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = MAIN_ACTIVITY_TITLE

        dbHelper = DBHelper(this)

        tasks_list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        tasks_list.adapter = TasksListAdapter(dbHelper.getAllTasks(), this)
    }

    override fun onResume() {
        super.onResume()
        tasks_list.adapter = TasksListAdapter(dbHelper.getAllTasks(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_task -> {
                startActivity(Intent(this, NewTaskActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClicked(task: TaskEntity) {
        val intent = Intent(this, TaskDetailsActivity::class.java)
        intent.putExtra(TASK_ID_EXTRA, task.id.toString())
        startActivity(intent)
    }
}
