package com.yts.data.mapper

import com.yts.data.table.UserTable
import com.yts.domain.entity.User

fun UserTable.map() = User(
    id, login, avatar_url, like
)

fun List<UserTable>.map(): MutableList<User> {
    val list: MutableList<User> = mutableListOf()

    for (userTable in this) {
        list.add(userTable.map())
    }
    return list
}