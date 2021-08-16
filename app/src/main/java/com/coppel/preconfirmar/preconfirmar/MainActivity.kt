package com.coppel.preconfirmar.preconfirmar


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.cedisropa.scanner.BrAppCompatActivity
import com.coppel.preconfirmar.R
import com.coppel.preconfirmar.application.AppConstants.ONBACK_PRESSED
import com.coppel.preconfirmar.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : BrAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    companion object {
        private val TAG = MainActivity::class.qualifiedName
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navigationDrawer :NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"Inicia MainAtivity------------>HomeFragment")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        drawerLayout = binding.drawerLayout


        val navController = findNavController(R.id.nav_host_fragment_content_main)
        navigationDrawer  = binding.navView
        navigationDrawer.setNavigationItemSelectedListener(this)

        appBarConfiguration = AppBarConfiguration(
            navController.graph,drawerLayout)


        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) //|| super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    private val getVolumeLevel = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val volume = intent?.extras!!["android.media.EXTRA_VOLUME_STREAM_VALUE"] as Int
            binding.appBarMain.toolbar.menu.findItem(R.id.action_settings).isVisible = volume == 0
        }
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction( "android.media.VOLUME_CHANGED_ACTION" )
        registerReceiver( getVolumeLevel, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver( getVolumeLevel )
    }

    override fun onBackPressed() {
        Log.d("TAG", ONBACK_PRESSED)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_salir -> {
                drawerLayout.closeDrawer(GravityCompat.START)

                val action = HomeFragmentDirections.actionNavHomeToSalirFragment()
                findNavController(R.id.nav_host_fragment_content_main).navigate(action)

            }

            R.id.nav_jaba -> {
                drawerLayout.closeDrawer(GravityCompat.START)

                val action = HomeFragmentDirections.actionNavHomeToJabaFragment()
                findNavController(R.id.nav_host_fragment_content_main).navigate(action)

            }
        }
        return true
    }
}

