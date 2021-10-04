package com.patricioglenn.alkemymoviesapp.data

data class ApiSesion(
    val success: Boolean,
    val guest_session_id: String,
    val expires_at: String
) {
}