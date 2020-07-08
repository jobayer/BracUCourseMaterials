package com.jobayr.bcm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bakhtiyor.gradients.Gradients
import com.jobayr.bcm.R
import com.jobayr.bcm.activities.BooksActivity
import com.jobayr.bcm.activities.NotesActivity
import com.jobayr.bcm.activities.SlidesActivity
import com.jobayr.bcm.extensions.openOnClick
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_home, container, false)
        init()
        return rootView
    }

    private fun init() {
      initUI()
        initCallback()
    }

    private fun initUI() {
        rootView.homeFragTVBooks.background = Gradients.highFlight()
        rootView.homeFragTVSlides.background = Gradients.orangeJuice()
        rootView.homeFragTVNotes.background = Gradients.nightCall()
    }

    private fun initCallback() {
        rootView.homeFragCVBooks.openOnClick(BooksActivity::class.java)
        rootView.homeFragCVSlides.openOnClick(SlidesActivity::class.java)
        rootView.homeFragCVNotes.openOnClick(NotesActivity::class.java)
    }

}