package com.yts.base.presentation.ui

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yts.base.R
import com.yts.base.databinding.ActivityMainBinding
import com.yts.base.presentation.base.BackDoubleClickFinishActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BackDoubleClickFinishActivity<ActivityMainBinding>() {
    private val model: MainViewModel by viewModel()

    override fun onLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.model = model
        setNavController()
    }

    private fun setNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_main) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)

    }

    override fun observer() {
    }
}