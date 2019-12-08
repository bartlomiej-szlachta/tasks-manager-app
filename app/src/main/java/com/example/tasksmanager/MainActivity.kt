package com.example.tasksmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = MAIN_ACTIVITY_TITLE

        dbHelper = DBHelper(this)

        tasks_list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        tasks_list.adapter = TasksListAdapter(dbHelper.getAllTasks())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_task -> {
                val intent = Intent(this, TaskActivity::class.java).apply {
                    putExtra(EXTRA_MODE, TaskActivityModeEnum.CREATE)
                }
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
