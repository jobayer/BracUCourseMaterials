package com.jobayr.bcm.extensions

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jobayr.bcm.R

fun AppCompatActivity.initToolbar(title: String) {
    supportActionBar?.title = title
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setHomeAsUpIndicator(resources.getDrawable(R.drawable.icon_back, theme))
}

fun View.makeVisible() {
    if (this.visibility == View.GONE) {
        this.visibility = View.VISIBLE
    } else if (this.visibility == View.INVISIBLE) {
        this.visibility = View.VISIBLE
    }
}

fun View.makeGone() {
    if (this.visibility == View.VISIBLE) {
        this.visibility = View.GONE
    } else if (this.visibility == View.INVISIBLE) {
        this.visibility = View.GONE
    }
}