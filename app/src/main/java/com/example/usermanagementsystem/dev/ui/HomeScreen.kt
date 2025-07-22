package com.example.usermanagementsystem.dev.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.usermanagementsystem.R
import com.example.usermanagementsystem.databinding.ActivityHomeScreenBinding
import com.example.usermanagementsystem.dev.core.viewmodel.NoteViewModel

class HomeScreen : AppCompatActivity() {

    private val viewModel: NoteViewModel by viewModels()
    private lateinit var binding: ActivityHomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, 0)
            insets
        }
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Profile -> {
                    navController.navigate(R.id.profileFragment)
                    true
                }
                R.id.notes -> {
                    navController.navigate(R.id.notesFragment)
                    true
                }
                else -> false
            }
        }
    }
}
