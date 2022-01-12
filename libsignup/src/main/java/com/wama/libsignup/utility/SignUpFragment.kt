package com.wama.libsignup.utility

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.wama.libsignup.R
import com.wama.libsignup.databinding.FragmentSignupBinding

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    var navController: NavController?= null

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initUI()
    }

    private fun initUI() {
        binding.signUpBtn.setOnClickListener {
            checkFieldsData()
        }

        binding.signInTv.setOnClickListener {
            navController!!.navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }

    private fun checkFieldsData() {
        val isValidEmail = binding.emailEt.validateEmail()
        val isValidPassword = binding.passwordEt.validatePassword()
        val isValidConfirmPassword = binding.confirmPasswordEt.validatePassword()
        val name = binding.fullNameEt.text.toString()
        if (name.isEmpty()) {
            binding.fullNameEt.error = getString(R.string.empty_name_error)
        } else if (!isValidEmail) {
            return
        } else if (!isValidPassword) {
            return
        } else if (!isValidConfirmPassword) {
            return
        } else if (binding.passwordEt.text.toString() != binding.confirmPasswordEt.text.toString()) {
            showSnackBar(
                binding.root,
                getString(R.string.password_confirm_matching_error)
            )
            return
        } else {
            Toast.makeText(context,"SignUp Successful",Toast.LENGTH_SHORT).show()
        }
    }

}