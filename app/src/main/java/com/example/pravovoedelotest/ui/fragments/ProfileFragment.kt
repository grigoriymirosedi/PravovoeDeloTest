package com.example.pravovoedelotest.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.pravovoedelotest.R
import com.example.pravovoedelotest.databinding.FragmentCodeInputBinding
import com.example.pravovoedelotest.databinding.FragmentPhoneInputBinding
import com.example.pravovoedelotest.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val arguments: ProfileFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.helloText.append(arguments.login)
        binding.tokenText.append(arguments.token)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}