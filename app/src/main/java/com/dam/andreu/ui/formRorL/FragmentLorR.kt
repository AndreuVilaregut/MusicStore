package com.dam.andreu.ui.formRorL

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.dam.andreu.R

import com.dam.andreu.ui.formRorL.fragments.LoginClient
import com.dam.andreu.ui.formRorL.fragments.LoginTreballador
import com.dam.andreu.ui.formRorL.fragments.Registrarse
import com.google.android.material.tabs.TabLayout

class FragmentLorR : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_l_or_r)

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        val viewPager: ViewPager = findViewById(R.id.viewPager)

        val pagerAdapter = LoginPagerAdapter(supportFragmentManager)

        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }
}

class LoginPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> LoginClient()
            1 -> Registrarse()
            2 -> LoginTreballador()
            else -> throw IllegalArgumentException("Posició no vàlida")
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Client"
            1 -> "Registrar-se"
            2 -> "Treballador"
            else -> null
        }
    }
}
