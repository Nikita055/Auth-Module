package com.wama.libsignup.utility

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wama.libsignup.R
import com.wama.libsignup.databinding.ForgotPasswordBottomsheetBinding

class ForgotPasswordBottomSheet : BaseBottomSheetDialog() {
    private lateinit var binding: ForgotPasswordBottomsheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ForgotPasswordBottomsheetBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding!!.resetBtn.setOnClickListener {
            val isValidEmail = binding!!.emailid.validateEmail()
            if (!isValidEmail) {
                return@setOnClickListener
            } else {
                // loginViewModel.callForgetPassword(binding!!.emailid.text.toString())
                dismiss()
            }

        }
    }
}