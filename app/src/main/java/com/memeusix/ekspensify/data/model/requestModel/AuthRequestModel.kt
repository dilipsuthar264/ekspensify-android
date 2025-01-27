package com.memeusix.ekspensify.data.model.requestModel

data class AuthRequestModel(
    val email: String? = null,
    val password: String? = null,
    val name: String? = null,
    val otp: String? = null,
)