package com.yts.data.source.local.dao

import androidx.room.*
import com.yts.data.table.UserTable
import io.reactivex.Observable

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserTable)

    @Query("SELECT * FROM User")
    suspend fun loadAll(): List<UserTable>

    @Query("SELECT * FROM User WHERE `like` = 1 ORDER BY login DESC")
    suspend fun likeLoadAll(): MutableList<UserTable>

    @Query("SELECT * FROM User WHERE id == :id ")
    suspend fun load(id: Int): UserTable?

    @Query("SELECT * FROM User WHERE login LIKE '%' || :query || '%' AND `like` = 1 ORDER BY login DESC")
    suspend fun load(query: String): MutableList<UserTable>

    @Delete
    suspend fun delete(user: UserTable)
}