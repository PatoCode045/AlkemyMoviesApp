package com.patricioglenn.alkemymoviesapp.network

data class ApiSesion(
    val success: Boolean,
    val guest_session_id: String,
    val expires_at: String
) {
}