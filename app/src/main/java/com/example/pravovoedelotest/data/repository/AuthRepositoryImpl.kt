package com.example.pravovoedelotest.data.repository

import com.example.pravovoedelotest.data.remote.api.PravoeDeloAPI
import com.example.pravovoedelotest.data.remote.dto.CodeResponseDTO
import com.example.pravovoedelotest.data.remote.dto.TokenResponseDTO
import com.example.pravovoedelotest.domain.repository.AuthRepository
import retrofit2.Response

class AuthRepositoryImpl(val api: PravoeDeloAPI): AuthRepository {

    override suspend fun getCode(login: String): Response<CodeResponseDTO> {
        return api.getCode(login = login)
    }

    override suspend fun getToken(login: String, password: String): Response<String> {
        return api.getToken(login = login, password = password)
    }
}