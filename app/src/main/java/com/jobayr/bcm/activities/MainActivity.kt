package com.jobayr.bcm.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.jobayr.bcm.R
import com.jobayr.bcm.fragments.AccountFragment
import com.jobayr.bcm.fragments.HomeFragment
import com.jobayr.bcm.fragments.GarageFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var fragManager: FragmentManager
    private lateinit var homeFrag: HomeFragment
    private lateinit var garageFrag: GarageFragment
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
            mainBottomBar.selectedItemId = R.id.navItemHome
        } else finishAffinity()
    }

    private fun init() {
        initCallback()
        initFrags()
    }

    private fun initFrags() {
        fragManager = supportFragmentManager
        homeFrag = HomeFragment()
        garageFrag = GarageFragment()
        accountFrag = AccountFragment()
        fragManager.beginTransaction().add(R.id.fragContainer, accountFrag)
            .hide(accountFrag).commit()
        fragManager.beginTransaction().add(R.id.fragContainer, garageFrag)
            .hide(garageFrag).commit()
        fragManager.beginTransaction().add(R.id.fragContainer, homeFrag)
            .commit()
        activeFrag = homeFrag
    }

    private fun initCallback() {
        mainBottomBar.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navItemHome -> {
                    if (activeFragIndex != 0) {
                        fragManager.beginTransaction().hide(activeFrag).show(homeFrag).commit()
                        activeFrag = homeFrag
                        activeFragIndex = 0
                    }
                }
                R.id.navItemTools -> {
                    if (activeFragIndex != 1) {
                        fragManager.beginTransaction().hide(activeFrag).show(garageFrag).commit()
                        activeFrag = garageFrag
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
            true
        }
    }

    fun changeFrag(index: Int) {
        when(index) {
            2 -> {
                if (activeFragIndex != 2) {
                    mainBottomBar.selectedItemId = R.id.navItemAccount
                    fragManager.beginTransaction().hide(activeFrag).show(accountFrag).commit()
                    activeFrag = accountFrag
                    activeFragIndex = 2
                }
            }
        }
    }

}