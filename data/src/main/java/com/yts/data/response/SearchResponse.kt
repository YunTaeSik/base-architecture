package com.yts.data.response

import com.yts.domain.entity.User

data class SearchResponse(
    val total_count: Int = 0,
    val items: List<User>? = null
)