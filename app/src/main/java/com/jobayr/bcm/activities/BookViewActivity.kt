package com.jobayr.bcm.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.jobayr.bcm.R
import com.jobayr.bcm.extensions.initToolbar
import com.jobayr.bcm.extensions.makeGone
import com.jobayr.bcm.extensions.makeVisible
import kotlinx.android.synthetic.main.activity_book_view.*

class BookViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_view)
        initToolbar(bookViewToolbar)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_book_view, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        initCallback()
    }

    private fun initCallback() {
        bookViewBIContainer.setOnClickListener {
            if (bookViewBIInfoContainer.visibility == View.GONE) {
                bookViewBIImageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.icon_arrow_up, theme))
                bookViewBIInfoContainer.makeVisible()
            } else {
                bookViewBIImageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.icon_arrow_down, theme))
                bookViewBIInfoContainer.makeGone()
            }
        }
        bookViewMContainer.setOnClickListener {
            if (bookViewMInfoContainer.visibility == View.GONE) {
                bookViewMImageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.icon_arrow_up, theme))
                bookViewMInfoContainer.makeVisible()
            } else {
                bookViewMImageView.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.icon_arrow_down, theme))
                bookViewMInfoContainer.makeGone()
            }
        }
    }

}