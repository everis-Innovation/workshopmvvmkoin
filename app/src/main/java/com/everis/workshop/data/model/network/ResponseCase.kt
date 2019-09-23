package com.everis.workshop.data.model.network

import com.everis.workshop.data.model.main.User

sealed class ResponseCase {

    data class UserResponse (val user: User): ResponseCase()
    data class ErrorResponse(val error: String): ResponseCase()
}