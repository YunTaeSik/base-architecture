package com.yts.data.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserTable(
    @PrimaryKey val id: Int,
    val login: String? = null,
    val avatar_url: String? = null,
    val like :Boolean? = null
)
