package com.chinmay.horobook

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.chinmay.horobook.view.DrawerNavigationFragments.FeedBackActivity
import com.chinmay.horobook.view.DrawerNavigationFragments.WebActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.container_fragment)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        bottomNavigationView.setupWithNavController(navController)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


//on click listener of navigation drawer
        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_share -> {
                    val sendIntent = Intent()
                    sendIntent.action = Intent.ACTION_SEND
                    sendIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Hey!!! Check out this amazing app where you can easily get All ritual songs and mantras : https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
                    )
                    sendIntent.type = "text/plain"
                    startActivity(sendIntent)
                }

                R.id.nav_privacy -> {
                    val intent = Intent(this, WebActivity::class.java)
                    intent.putExtra("url", UrlConstants.privacy_policy)
                    startActivity(intent)
                }

                R.id.nav_about -> {
                    val intent = Intent(this, WebActivity::class.java)
                    intent.putExtra("url", UrlConstants.about_us)
                    startActivity(intent)
                }

                R.id.nav_terms -> {
                    val intent = Intent(this, WebActivity::class.java)
                    intent.putExtra("url", UrlConstants.terms)
                    startActivity(intent)
                }

                R.id.nav_feedback -> {
                    val intent = Intent(this, FeedBackActivity::class.java)
                    startActivity(intent)
                }

            }
            true

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}