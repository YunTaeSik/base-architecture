package com.yts.domain.usecase

class GetTokenUseCase(private val token: String) {
    fun invoke(): String = "token $token"
}