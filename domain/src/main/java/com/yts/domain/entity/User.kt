package com.yts.domain.entity

data class User(
    val id: Int,
    val login: String? = null,
    val avatar_url: String? = null,
    var like :Boolean? = null
)