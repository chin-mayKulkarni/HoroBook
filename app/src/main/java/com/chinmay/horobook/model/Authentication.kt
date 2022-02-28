package com.chinmay.horobook.model

import com.google.gson.annotations.SerializedName


data class Authentication(
    @SerializedName("Auth_key")
    val auth_key : String?,

    @SerializedName("app_version")
    val app_version: String?,

    @SerializedName("app_force_update")
    val app_force_update : String?,

    @SerializedName("user_type_new")
    val user_type_new : String?
)