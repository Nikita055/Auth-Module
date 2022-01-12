package com.wama.signupmodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.wama.signupmodule.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
 private lateinit var binding : ActivityMainBinding
 lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
      //  initUi()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setNavigation()
    }

    private fun setNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_main)
        navController.graph.startDestination = R.id.home
    }
}