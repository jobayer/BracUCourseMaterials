package com.jobayr.bcm.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.jobayr.bcm.R
import com.jobayr.bcm.fragments.AccountFragment
import com.jobayr.bcm.fragments.HomeFragment
import com.jobayr.bcm.fragments.ToolsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var fragManager: FragmentManager
    private lateinit var homeFrag: HomeFragment
    private lateinit var toolsFrag: ToolsFragment
    private lateinit var accountFrag: AccountFragment
    private lateinit var activeFrag: Fragment
    private var activeFragIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onBackPressed() {
        if (activeFragIndex != 0) {
            fragManager.beginTransaction().hide(activeFrag).show(homeFrag).commit()
            activeFrag = homeFrag
            activeFragIndex = 0
            mainNavBar.setItemSelected(R.id.navItemHome)
        } else finishAffinity()
    }

    private fun init() {
        mainNavBar.setItemSelected(R.id.navItemHome)
        initCallback()
        initFrags()
    }

    private fun initFrags() {
        fragManager = supportFragmentManager
        homeFrag = HomeFragment()
        toolsFrag = ToolsFragment()
        accountFrag = AccountFragment()
        fragManager.beginTransaction().add(R.id.fragContainer, accountFrag)
            .hide(accountFrag).commit()
        fragManager.beginTransaction().add(R.id.fragContainer, toolsFrag)
            .hide(toolsFrag).commit()
        fragManager.beginTransaction().add(R.id.fragContainer, homeFrag)
            .commit()
        activeFrag = homeFrag
    }

    private fun initCallback() {
        mainNavBar.setOnItemSelectedListener {
            when(it) {
                R.id.navItemHome -> {
                    if (activeFragIndex != 0) {
                        fragManager.beginTransaction().hide(activeFrag).show(homeFrag).commit()
                        activeFrag = homeFrag
                        activeFragIndex = 0
                    }
                }
                R.id.navItemTools -> {
                    if (activeFragIndex != 1) {
                        fragManager.beginTransaction().hide(activeFrag).show(toolsFrag).commit()
                        activeFrag = toolsFrag
                        activeFragIndex = 1
                    }
                }
                R.id.navItemAccount -> {
                    if (activeFragIndex != 2) {
                        fragManager.beginTransaction().hide(activeFrag).show(accountFrag).commit()
                        activeFrag = accountFrag
                        activeFragIndex = 2
                    }
                }
            }
        }
    }

}