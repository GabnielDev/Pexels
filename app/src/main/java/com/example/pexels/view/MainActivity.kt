package com.example.pexels.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.pexels.R
import com.example.pexels.databinding.ActivityMainBinding
import com.example.pexels.fragment.ImageFragment
import com.example.pexels.fragment.VideoFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val fragmentImage: Fragment = ImageFragment()
    private val fragmentVideo: Fragment = VideoFragment()

    private val fm: FragmentManager = supportFragmentManager

    var active: Fragment = fragmentImage

    lateinit var menu: Menu
    lateinit var menuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpBotNav()

    }

    private fun setUpBotNav() {
        fm.beginTransaction().add(R.id.flContainer, fragmentImage).show(fragmentImage).commit()
        fm.beginTransaction().add(R.id.flContainer, fragmentVideo).hide(fragmentVideo).commit()

        menu = binding.botNav.menu
        menuItem = menu.get(0)
        menuItem.isChecked = true

        binding.botNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_image -> {
                    callFragment(0, fragmentImage)
                }
                R.id.nav_video -> {
                    callFragment(1, fragmentVideo)
                }
            }
            false
        }
    }

    fun callFragment(int: Int, fragment: Fragment) {
        menuItem = menu.getItem(int)
        menuItem.isChecked = true
        fm.beginTransaction().hide(active).show(fragment).commit()
        active = fragment
    }

}