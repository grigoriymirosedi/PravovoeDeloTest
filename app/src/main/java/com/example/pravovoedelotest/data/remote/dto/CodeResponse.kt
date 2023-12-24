package com.example.pravovoedelotest.data.remote.dto

import com.example.pravovoedelotest.domain.models.CodeResponse

data class CodeResponseDTO(val code: String, val status: String)

fun CodeResponseDTO.toCodeResponse(): CodeResponse = CodeResponse(code = code, status = status)