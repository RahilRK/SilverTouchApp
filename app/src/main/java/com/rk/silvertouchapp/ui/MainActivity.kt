package com.rk.silvertouchapp.ui

import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.rk.silvertouchapp.R
import com.rk.silvertouchapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var tag = this.javaClass.simpleName

    lateinit var binding: ActivityMainBinding

    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var drawerLayout: DrawerLayout
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragment)
        binding.navigationView.setupWithNavController(navController)

        //todo for hamburger menu, attach fragments
        appBarConfiguration = AppBarConfiguration(navController.graph,binding.drawerLayout)
//        setupActionBarWithNavController(navController,appBarConfiguration)
    }

    //todo for expand/collapse menu
    override fun onSupportNavigateUp(): Boolean {

//        val navController = findNavController(R.id.fragment)
        return navController.navigateUp(appBarConfiguration)||super.onSupportNavigateUp()
    }

    fun setToolbar(toolbar: Toolbar?): Toolbar? {

        drawerLayout = binding.drawerLayout

        if (toolbar != null) {
            setSupportActionBar(toolbar)
            toggle = ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()
        }
        return toolbar
    }
}