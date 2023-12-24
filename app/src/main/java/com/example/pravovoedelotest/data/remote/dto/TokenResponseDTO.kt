package com.example.pravovoedelotest.data.remote.dto

import android.media.session.MediaSession.Token
import com.example.pravovoedelotest.domain.models.TokenResponse

data class TokenResponseDTO(val token: String)

fun TokenResponseDTO.toTokenResponse(): TokenResponse = TokenResponse(token = token)
