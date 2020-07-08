package com.jobayr.bcm.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jobayr.bcm.R
import com.jobayr.bcm.adapters.BookAdapter
import com.jobayr.bcm.extensions.initToolbar
import com.jobayr.bcm.models.Book
import kotlinx.android.synthetic.main.activity_books.*

class BooksActivity : AppCompatActivity() {

    private lateinit var books: MutableList<Book>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)
        initToolbar(booksToolbar)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_books, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.menuItemBooksSort -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        initData()
        initRecyclerView()
    }

    private fun initData() {
        books = mutableListOf()
        books.add(Book())
        books.add(Book())
        books.add(Book())
        books.add(Book())
        books.add(Book())
        books.add(Book())
        books.add(Book())
        books.add(Book())
        books.add(Book())
        books.add(Book())
    }

    private fun initRecyclerView() {
        booksRecyclerView.layoutManager = LinearLayoutManager(this)
        booksRecyclerView.adapter = BookAdapter(this, books)
    }

}