package com.example.pravovoedelotest.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.pravovoedelotest.data.remote.api.RetrofitClient
import com.example.pravovoedelotest.data.repository.AuthRepositoryImpl
import com.example.pravovoedelotest.databinding.ActivityMainBinding
import com.example.pravovoedelotest.ui.viewmodels.AuthViewModel
import com.example.pravovoedelotest.ui.viewmodels.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = AuthRepositoryImpl(RetrofitClient.providePostsApi())

        viewModel = ViewModelFactory(repository).create(AuthViewModel::class.java)
    }
}