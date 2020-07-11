package com.jobayr.bcm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bakhtiyor.gradients.Gradients
import com.google.firebase.auth.FirebaseAuth
import com.jobayr.bcm.R
import com.jobayr.bcm.activities.MainActivity
import com.jobayr.bcm.extensions.makeGone
import com.jobayr.bcm.extensions.makeVisible
import kotlinx.android.synthetic.main.fragment_garage.view.*

class GarageFragment : Fragment() {

    private lateinit var rootView: View
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_garage, container, false)
        init()
        return rootView
    }

    private fun init() {
        initFirebase()
        initUI()
        initCallback()
    }

    private fun initFirebase() {
        firebaseAuth = FirebaseAuth.getInstance()
    }

    private fun initUI() {
        if (firebaseAuth.currentUser != null) {
            rootView.garageFragSignInContainer.makeGone()
            rootView.garageFragItemsContainer.makeVisible()
            rootView.garageFragTVAddItem.background = Gradients.midnightBloom()
        } else {
            rootView.garageFragItemsContainer.makeGone()
            rootView.garageFragSignInContainer.makeVisible()
        }
    }

    private fun initCallback() {
        rootView.garageFragSignInBtn.setOnClickListener {
            firebaseAuth.currentUser?.let {
                (activity as MainActivity).changeFrag(2)
            }
        }
    }

}