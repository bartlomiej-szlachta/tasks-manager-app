package com.example.tasksmanager

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.*

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    // TODO: constructor should be private (singleton)

    companion object {
        private const val DB_NAME = "tasks.db"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "tasks"
        private const val COLUMN_ID_NAME = "id"
        private const val COLUMN_TITLE_NAME = "title"
        private const val COLUMN_DESCRIPTION_NAME = "description"
        private const val COLUMN_PRIORITY_NAME = "priority"
        private const val COLUMN_STATUS_NAME = "status"
        private const val COLUMN_VALID_FROM_NAME = "valid_from"
        private const val DATE_FORMAT = "dd.MM.yyyy HH:mm"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$COLUMN_ID_NAME INTEGER PRIMARY KEY, " +
                "$COLUMN_TITLE_NAME TEXT NOT NULL, " +
                "$COLUMN_DESCRIPTION_NAME TEXT NOT NULL, " +
                "$COLUMN_PRIORITY_NAME TEXT NOT NULL, " +
                "$COLUMN_STATUS_NAME TEXT NOT NULL, " +
                "$COLUMN_VALID_FROM_NAME DATE NOT NULL" +
                ");"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME;")
        onCreate(db)
    }

    fun getAllTasks(): List<TaskEntity> {
        val query = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_STATUS_NAME;"
        val db = this.readableDatabase
        val tasks = mutableListOf<TaskEntity>()
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                tasks.add(initializeTask(cursor))
            } while (cursor.moveToNext())
        }

        sortTasks(tasks)

        cursor.close()
        db.close()
        return tasks
    }

    fun getTaskById(taskId: Int): TaskEntity {
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID_NAME = $taskId;"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (!cursor.moveToFirst()) {
            throw RuntimeException()
        }

        val task = initializeTask(cursor)

        cursor.close()
        db.close()

        return task
    }

    fun createTask(title: String, priority: String, description: String) {
        val db = this.writableDatabase
        val value: ContentValues = contentValuesOf()
        value.put(COLUMN_TITLE_NAME, title)
        value.put(COLUMN_DESCRIPTION_NAME, description)
        value.put(COLUMN_STATUS_NAME, STATUS_NEW)
        value.put(COLUMN_PRIORITY_NAME, priority)
        value.put(COLUMN_VALID_FROM_NAME, SimpleDateFormat(DATE_FORMAT, Locale.US).format(Date()))
        db.insert(TABLE_NAME, null, value)
        db.close()
    }

    fun changeStatus(id: Int, newStatus: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_STATUS_NAME, newStatus)
        db.update(TABLE_NAME, values, "id=" + id.toLong(), null)
        db.close()
    }

    private fun initializeTask(cursor: Cursor): TaskEntity {
        val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_NAME))
        val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE_NAME))
        val content = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION_NAME))
        val status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS_NAME))
        val priority = cursor.getString(cursor.getColumnIndex(COLUMN_PRIORITY_NAME))
        val validFrom = cursor.getString(cursor.getColumnIndex(COLUMN_VALID_FROM_NAME))
        return TaskEntity(id, title, content, status, priority, validFrom)
    }

    private fun sortTasks(tasks: List<TaskEntity>) {
        // TODO: sort tasks by status here
    }
}