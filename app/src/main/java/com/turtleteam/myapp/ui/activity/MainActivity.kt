package com.turtleteam.myapp.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.turtleteam.myapp.R
import com.turtleteam.myapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationMenu = findViewById<BottomNavigationView>(R.id.nav_bar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.authFragment,
                R.id.registerButton,
                R.id.participateFragment,
                R.id.createEventFragment,
                R.id.stepFragment,
                R.id.createStepFragment,
                R.id.profileFragment
            )
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.authFragment -> bottomNavigationMenu.visibility = View.GONE
                R.id.registerFragment -> bottomNavigationMenu.visibility = View.GONE
                R.id.createEventFragment -> bottomNavigationMenu.visibility = View.GONE
                R.id.editEventFragment -> bottomNavigationMenu.visibility = View.GONE
                R.id.stepFragment -> bottomNavigationMenu.visibility = View.GONE
                R.id.createStepFragment -> bottomNavigationMenu.visibility = View.GONE
                R.id.editStepFragment -> bottomNavigationMenu.visibility = View.GONE
                else -> bottomNavigationMenu.visibility = View.VISIBLE
            }
        }

        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNavigationMenu.setupWithNavController(navController)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}