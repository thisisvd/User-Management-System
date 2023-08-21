package com.example.usermanagementsystem.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.usermanagementsystem.R
import com.example.usermanagementsystem.databinding.ActivityMainBinding
import com.example.usermanagementsystem.ui.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    // view binding
    private lateinit var binding: ActivityMainBinding

    // nav controller
    private lateinit var navController: NavController

    // view model
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {

            // set up toolbar with action bar
            setSupportActionBar(toolbar)

            // nav host init
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
            navController = navHostFragment.navController
            setupActionBarWithNavController(navController)

            // setup db
            viewModel.setUpDatabase(this@MainActivity)
        }
    }

    // on back pressed setup
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}