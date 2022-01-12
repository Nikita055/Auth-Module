package com.wama.libsignup.utility

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.wama.libsignup.R
import com.wama.libsignup.databinding.FragmentOtpBinding
import com.wama.libsignup.databinding.FragmentSignupBinding

class OtpFragment : Fragment() {
    private lateinit var binding:FragmentOtpBinding
    private lateinit var number:String
    private lateinit var countryCode:String

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    private fun initUi() {
        binding.mobileTel.setText("$countryCode $number")
        binding.button.setOnClickListener {
            val getOtp = binding.otpEdit.text!!.trim()
            if (getOtp.isNotEmpty() && getOtp.length >= 6) {
                binding.root.hideKeyboard()
            }
        }

        binding.otpEdit.onSubmit {
          //  otpVerifyViewModel.registerMobile(countryCode,number,binding.otpEdit.text.toString())
        }

        binding.otpEdit.afterTextChanged {
            if (it.length>=6){
                binding.button.setEnable()
                binding.button.backgroundTintList = ContextCompat.getColorStateList(requireActivity(), R.color.primary_yellow)
            }else{
                binding.button.backgroundTintList = ContextCompat.getColorStateList(requireActivity(), R.color.gray_20)
                binding.button.setDisable()
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtpBinding.inflate(layoutInflater)
        return binding.root
    }
}