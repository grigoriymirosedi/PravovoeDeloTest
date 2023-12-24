package com.example.pravovoedelotest.ui.viewmodels

import android.media.session.MediaSession.Token
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pravovoedelotest.data.remote.dto.CodeResponseDTO
import com.example.pravovoedelotest.data.remote.dto.TokenResponseDTO
import com.example.pravovoedelotest.data.remote.dto.toCodeResponse
import com.example.pravovoedelotest.data.remote.dto.toTokenResponse
import com.example.pravovoedelotest.domain.models.CodeResponse
import com.example.pravovoedelotest.domain.models.TokenResponse
import com.example.pravovoedelotest.domain.repository.AuthRepository
import com.example.pravovoedelotest.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _codeResponseData: MutableLiveData<Resource<CodeResponse>> = MutableLiveData()
    val codeResponseData: LiveData<Resource<CodeResponse>>
        get() = _codeResponseData

    private val _tokenResponseData: MutableLiveData<Resource<String>> = MutableLiveData()
    val tokenResponseData: LiveData<Resource<String>>
        get() = _tokenResponseData

    suspend fun sendCode(login: String) = viewModelScope.launch {
        _codeResponseData.postValue(Resource.Loading())
        val response = authRepository.getCode(login)
        _codeResponseData.postValue(handleCodeResponse(response))
    }

    suspend fun getToken(login: String, password: String) = viewModelScope.launch {
        _tokenResponseData.postValue(Resource.Loading())
        val response = authRepository.getToken(login, password)
        _tokenResponseData.postValue(handleTokenResponse(response))
    }

    private fun handleTokenResponse(response: Response<String>): Resource<String> {
        if(response.isSuccessful) {
            response.body()?.let { responseResult ->
                return Resource.Success(responseResult)
            }
        }
        return Resource.Error(response.errorBody().toString())
    }

    private fun handleCodeResponse(response: Response<CodeResponseDTO>): Resource<CodeResponse> {
        if(response.isSuccessful) {
            response.body()?.let { responseResult ->
                return Resource.Success(responseResult.toCodeResponse())
            }
        }
        return Resource.Error(response.errorBody().toString())
    }

}