package com.example.pravovoedelotest.data.remote.api

import com.example.pravovoedelotest.data.remote.dto.CodeResponseDTO
import com.example.pravovoedelotest.data.remote.dto.TokenResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PravoeDeloAPI {

    @GET("v1/getCode")
    suspend fun getCode(@Query("login") login: String): Response<CodeResponseDTO>

    @GET("v1/getToken")
    suspend fun getToken(@Query("login") login: String, @Query("password") password: String): Response<String>
}