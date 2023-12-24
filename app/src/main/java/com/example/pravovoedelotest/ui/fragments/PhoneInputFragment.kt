package com.example.pravovoedelotest.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.pravovoedelotest.data.remote.api.RetrofitClient
import com.example.pravovoedelotest.data.repository.AuthRepositoryImpl
import com.example.pravovoedelotest.databinding.FragmentPhoneInputBinding
import com.example.pravovoedelotest.ui.viewmodels.AuthViewModel
import com.example.pravovoedelotest.ui.viewmodels.ViewModelFactory
import com.example.pravovoedelotest.utils.Resource
import com.example.pravovoedelotest.utils.toRawPhoneNumber
import kotlinx.coroutines.launch
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class PhoneInputFragment : Fragment() {

    private var _binding: FragmentPhoneInputBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPhoneInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPhoneFormattingText()
        buttonIsActive()
        handleSendCodeButton()

        val repository = AuthRepositoryImpl(RetrofitClient.providePostsApi())
        viewModel = ViewModelFactory(repository).create(AuthViewModel::class.java)
        viewModel.codeResponseData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val login = binding.phoneEditText.text.toString()
                    val code = it.data!!.code
                    Log.d("123123", code.toString())
                    findNavController().navigate(PhoneInputFragmentDirections
                        .actionPhoneInputFragmentToCodeInputFragment(login, code))
                }
                is Resource.Error -> binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun handleSendCodeButton() {
        binding.phoneAcceptButton.setOnClickListener {
            lifecycleScope.launch {
                viewModel.sendCode(binding.phoneEditText.text.toString().toRawPhoneNumber())
            }
        }
    }

    private fun buttonIsActive() {
        binding.phoneEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.phoneAcceptButton.isEnabled = s.toString().toRawPhoneNumber().length == 11
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun setPhoneFormattingText() {
        val slots = UnderscoreDigitSlotsParser().parseSlots("___) ___-__-__")
        val mask = MaskImpl.createTerminated(slots)
        val watcher = MaskFormatWatcher(mask)
        watcher.installOn(binding.phoneEditText)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
