package com.bielik.progresstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bielik.progresstracker.databinding.ActivityMainBinding
import com.bielik.progresstracker.fragments.FirstFragment
import com.bielik.progresstracker.fragments.FourthFragment
import com.bielik.progresstracker.fragments.SecondFragment
import com.bielik.progresstracker.fragments.ThirdFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        defineFragment()
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

    private fun defineFragment() {
        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()
        val thirdFragment = ThirdFragment()
        val fourthFragment = FourthFragment()

        setCurrentFragment(firstFragment)
        binding.bottomNavView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miHome -> setCurrentFragment(firstFragment)
                R.id.miProgress -> setCurrentFragment(secondFragment)
                R.id.miAlerts -> setCurrentFragment(thirdFragment)
                R.id.miProfile -> setCurrentFragment(fourthFragment)
            }
            true
        }
    }
}