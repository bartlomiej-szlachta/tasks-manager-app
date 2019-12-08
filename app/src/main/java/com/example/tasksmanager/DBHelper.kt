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

    companion object {
        private const val DB_NAME = "tasks.db"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "tasks"
        private const val COLUMN_ID_NAME = "id"
        private const val COLUMN_TITLE_NAME = "title"
        private const val COLUMN_CONTENT_NAME = "content"
        private const val COLUMN_PRIORITY_NAME = "priority"
        private const val COLUMN_STATUS_NAME = "status"
        private const val COLUMN_VALID_FROM_NAME = "valid_from"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$COLUMN_ID_NAME INTEGER PRIMARY KEY, " +
                "$COLUMN_TITLE_NAME TEXT NOT NULL, " +
                "$COLUMN_CONTENT_NAME TEXT NOT NULL, " +
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
        val query = "SELECT * FROM $TABLE_NAME;"
        val db = this.readableDatabase
        val people = mutableListOf<TaskEntity>()
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                people.add(initializeTask(cursor))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return people
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

    fun createTask(task: TaskEntity) {
        val db = this.writableDatabase
        val value: ContentValues = initializeContentValues(task)
        db.insert(TABLE_NAME, null, value)
        db.close()
    }

    fun changeStatus(id: Int, newStatus: TaskStatusEnum) {
        val db = this.writableDatabase
        TODO()
    }

    fun editTask(id: Int, task: TaskEntity) {
        val db = this.writableDatabase
        val value: ContentValues = initializeContentValues(task)
        db.update(TABLE_NAME, value, "$COLUMN_ID_NAME =? ", arrayOf(id.toString()))
        db.close()
    }

    fun deleteTask(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID_NAME =? ", arrayOf(id.toString()))
        db.close()
    }

    private fun initializeTask(cursor: Cursor): TaskEntity {
        val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID_NAME))
        val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE_NAME))
        val content = cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT_NAME))
        val statusName = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS_NAME))
        val priorityName = cursor.getString(cursor.getColumnIndex(COLUMN_PRIORITY_NAME))
        val validFromString = cursor.getString(cursor.getColumnIndex(COLUMN_VALID_FROM_NAME))
        val validFromDate: Date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            .parse(validFromString)!!
        return TaskEntity(id, title, content, TaskStatusEnum.valueOf(statusName),
            TaskPriorityEnum.valueOf(priorityName), validFromDate)
    }

    private fun initializeContentValues(task: TaskEntity): ContentValues {
        val value: ContentValues = contentValuesOf()
        value.put(COLUMN_TITLE_NAME, task.title)
        value.put(COLUMN_CONTENT_NAME, task.content)
        value.put(COLUMN_STATUS_NAME, task.status.value)
        value.put(COLUMN_PRIORITY_NAME, task.priority.value)
        value.put(COLUMN_VALID_FROM_NAME, SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
            .format(task.validFrom))
        return value
    }
}