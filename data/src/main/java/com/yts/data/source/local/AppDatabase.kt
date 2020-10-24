package com.yts.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yts.data.source.local.dao.UserDao
import com.yts.data.table.UserTable

@Database(entities = [UserTable::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    companion object {
        const val DB_NAME = "ytsTest.db"
    }
}
