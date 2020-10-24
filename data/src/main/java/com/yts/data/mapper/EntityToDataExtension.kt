package com.yts.data.mapper

import com.yts.data.table.UserTable
import com.yts.domain.entity.User

fun User.map() = UserTable(
 id, login, avatar_url, like
)