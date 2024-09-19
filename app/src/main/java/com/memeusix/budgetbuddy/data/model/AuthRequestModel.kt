package com.memeusix.budgetbuddy.data.model

data class AuthRequestModel(
    val email: String? = null,
    val password: String? = null,
    val name: String? = null,
    val otp: String? = null,
)