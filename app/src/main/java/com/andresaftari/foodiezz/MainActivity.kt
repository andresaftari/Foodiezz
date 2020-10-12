package com.andresaftari.foodiezz

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.andresaftari.foodiezz.view.about.AboutFragment
import com.andresaftari.foodiezz.view.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var currentId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_nav.apply {
            itemIconTintList = null
            setOnNavigationItemSelectedListener(onNavigationItemSelected)
        }

        supportFragmentManager.commit { replace(R.id.fragment_container, HomeFragment()) }
    }

    private val onNavigationItemSelected =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val fragments = fun(fragmentId: Int, fragment: Fragment) {
                if (currentId != fragmentId) supportFragmentManager.commit {
                    replace(R.id.fragment_container, fragment)
                }
            }

            when (item.itemId) {
                R.id.action_home -> fragments(R.id.action_home, HomeFragment())
                R.id.action_about -> fragments(R.id.action_about, AboutFragment())
                R.id.action_favorite -> {
                    val uri = Uri.parse("foodiezz://favorite")
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                }
            }

            currentId = item.itemId
            true
        }
}