package com.example.pravovoedelotest.domain.repository

import com.example.pravovoedelotest.data.remote.dto.CodeResponseDTO
import com.example.pravovoedelotest.data.remote.dto.TokenResponseDTO
import retrofit2.Response

interface AuthRepository {

    suspend fun getCode(login: String): Response<CodeResponseDTO>

    suspend fun getToken(login: String, password: String): Response<String>

}