package com.example.pravovoedelotest.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pravovoedelotest.data.remote.api.RetrofitClient
import com.example.pravovoedelotest.data.repository.AuthRepositoryImpl
import com.example.pravovoedelotest.databinding.FragmentCodeInputBinding
import com.example.pravovoedelotest.databinding.FragmentPhoneInputBinding
import com.example.pravovoedelotest.ui.viewmodels.AuthViewModel
import com.example.pravovoedelotest.ui.viewmodels.ViewModelFactory
import com.example.pravovoedelotest.utils.Resource
import com.example.pravovoedelotest.utils.toRawPhoneNumber
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CodeInputFragment : Fragment() {

    private var _binding: FragmentCodeInputBinding? = null
    private val binding get() = _binding!!


    private val arguments: CodeInputFragmentArgs by navArgs()

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCodeInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = AuthRepositoryImpl(RetrofitClient.providePostsApi())
        viewModel = ViewModelFactory(repository).create(AuthViewModel::class.java)

        binding.codeInputView.setCompleteListener { complete ->
            Log.d("123123", arguments.code)
            Log.d("123133", "{${arguments.login.toRawPhoneNumber()}, ${arguments.code}")

            if(complete && binding.codeInputView.text != arguments.code) {
                binding.codeInputView.setCodeItemErrorLineDrawable()
                showErrorViews()
            }
            else if (complete && binding.codeInputView.text == arguments.code)
            {
                lifecycleScope.launch {
                    viewModel.getToken(login = arguments.login.toRawPhoneNumber(), password = arguments.code)
                    viewModel.tokenResponseData.observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Loading -> return@observe
                            is Resource.Success -> {
                                val token = it.data
                                findNavController().navigate(CodeInputFragmentDirections
                                    .actionCodeInputFragmentToProfileFragment(arguments.login.toRawPhoneNumber(), token.toString()))
                            }
                            is Resource.Error -> return@observe
                        }
                    }
                }
            }
        }

        binding.sendRequestCode.setOnClickListener {
            hideErrorText()
            binding.codeInputView.resetCodeItemLineDrawable()
            lifecycleScope.launch {
                viewModel.sendCode(arguments.login.toRawPhoneNumber())
                buttonDelayTimer()
            }
        }
    }

    suspend fun buttonDelayTimer() {
        var buttonText = binding.sendRequestCode.text.toString()
        binding.sendRequestCode.isEnabled = false
        for(i in 10 downTo 0) {
            var newText = "$buttonText $i"
            binding.sendRequestCode.text = newText
            delay(1000L)
        }
        binding.sendRequestCode.text = buttonText
        binding.sendRequestCode.isEnabled = true
    }

    private fun hideErrorText() {
        binding.errorText.visibility = View.INVISIBLE
    }

    private fun showErrorViews() {
        binding.errorText.visibility = View.VISIBLE
        binding.sendRequestCode.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}