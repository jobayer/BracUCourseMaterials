package com.jobayr.bcm.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jobayr.bcm.R
import com.jobayr.bcm.adapters.BookAdapter
import com.jobayr.bcm.extensions.*
import com.jobayr.bcm.models.Book
import kotlinx.android.synthetic.main.activity_books.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BooksActivity : AppCompatActivity() {

    private lateinit var firebaseDB: DatabaseReference
    private var sortedAlphabetically: Boolean = false
    private lateinit var books: MutableList<Book>
    private lateinit var lastBook: Book
    private var filterSelected = -1
    private var selectedDept = -1

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
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.booksMenuItemBooksSort -> {
                sortedAlphabetically = if (!sortedAlphabetically) {
                    item.setIcon(R.drawable.icon_sort_alp)
                    true
                } else {
                    item.setIcon(R.drawable.icon_sort_num)
                    false
                }
            }
            R.id.booksMenuItemBooksFilter -> {
                if (bookChipContainer.visibility == View.GONE) {
                    bookChipContainer.makeVisible()
                } else bookChipContainer.makeGone()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        initFirebase()
        initRecyclerView()
        initCallback()
        getBooks()
        getMoreLatestBooks()
        booksSRL.setOnRefreshListener {
            getBooks()
        }
    }

    private fun initFirebase() {
        firebaseDB = Firebase.database.reference
    }

    private fun initRecyclerView() {
        books = mutableListOf()
        booksRecyclerView.layoutManager = LinearLayoutManager(this)
        booksRecyclerView.adapter = BookAdapter(books)
    }

    private fun initCallback() {
        booksMyDeptChip.setOnClickListener {
            if (booksMyDeptChip.isChecked) {
                if (booksDeptChip.isChecked) {
                    booksDeptChip.isChecked = false
                }
                filterSelected = 0
                getBooks()
            } else {
                if (booksDeptChip.isChecked) {
                    booksDeptChip.isChecked = false
                }
                filterSelected = -1
                getBooks()
            }
        }
        booksDeptChip.setOnClickListener {
            if (booksDeptChip.isChecked) {
                if (booksMyDeptChip.isChecked) {
                    booksMyDeptChip.isChecked = false
                }
                filterSelected = 1
                showDeptChooserDialog()
            } else {
                if (booksMyDeptChip.isChecked) {
                    booksMyDeptChip.isChecked = false
                }
                filterSelected = -1
                getBooks()
            }
        }
    }

    private fun getBooks() {
       when(filterSelected) {
           -1 -> getLatestBooks()
           0 -> getMyDeptBooks()
           1 -> getDeptWiseBook()
       }
    }

    private fun getLatestBooks() {
        CoroutineScope(Dispatchers.IO).launch {
            books.clear()
            firebaseDB.child("books")
                .limitToFirst(9)
                .orderByKey()
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {}

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (booksSRL.isRefreshing) {
                            booksSRL.isRefreshing = false
                        }
                        if (snapshot.exists()) {
                            val lastItemIndex = snapshot.children.count()
                            for ((index, item) in snapshot.children.withIndex()) {
                                val data = item.getValue(Book::class.java)!!
                                data.nodeKey = item.key
                                if (index != lastItemIndex - 1) {
                                    if (!books.contains(data)) {
                                        books.add(data)
                                    }
                                } else {
                                    lastBook = data
                                    this@BooksActivity.booksRecyclerView.adapter?.notifyDataSetChanged()
                                }
                            }
                        } else showErrorToast("No Books Found")
                    }
                })
        }
    }

    private fun getMyDeptBooks() {
        CoroutineScope(Dispatchers.IO).launch {
            books.clear()
            firebaseDB.child("books")
                .limitToFirst(9)
                .orderByKey()
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {}

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (booksSRL.isRefreshing) {
                            booksSRL.isRefreshing = false
                        }
                        if (snapshot.exists()) {
                            val lastItemIndex = snapshot.children.count()
                            for ((index, item) in snapshot.children.withIndex()) {
                                val data = item.getValue(Book::class.java)!!
                                data.nodeKey = item.key
                                if (index != lastItemIndex - 1) {
                                    if (!books.contains(data)) {
                                        books.add(data)
                                    }
                                } else {
                                    lastBook = data
                                    this@BooksActivity.booksRecyclerView.adapter?.notifyDataSetChanged()
                                }
                            }
                        } else showErrorToast("No Books Found")
                    }
                })
        }
    }

    private fun getDeptWiseBook() {
        CoroutineScope(Dispatchers.IO).launch {
            books.clear()
            firebaseDB.child("books")
                .limitToFirst(9)
                .orderByKey()
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {}

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (booksSRL.isRefreshing) {
                            booksSRL.isRefreshing = false
                        }
                        if (snapshot.exists()) {
                            val lastItemIndex = snapshot.children.count()
                            for ((index, item) in snapshot.children.withIndex()) {
                                val data = item.getValue(Book::class.java)!!
                                data.nodeKey = item.key
                                if (index != lastItemIndex - 1) {
                                    if (!books.contains(data)) {
                                        books.add(data)
                                    }
                                } else {
                                    lastBook = data
                                    this@BooksActivity.booksRecyclerView.adapter?.notifyDataSetChanged()
                                }
                            }
                        } else showErrorToast("No Books Found")
                    }
                })
        }
    }

    private fun getMoreLatestBooks() {
        CoroutineScope(Dispatchers.IO).launch {
            booksRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        firebaseDB.child("books")
                            .startAt(lastBook.nodeKey)
                            .limitToFirst(9)
                            .orderByKey()
                            .addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onCancelled(error: DatabaseError) {}

                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()) {
                                        val lastItemIndex = snapshot.children.count()

                                        for ((index, item) in snapshot.children.withIndex()) {
                                            val data = item.getValue(Book::class.java)!!
                                            data.nodeKey = item.key
                                            if (index != lastItemIndex - 1) {
                                                if (!books.contains(data)) {
                                                    books.add(data)
                                                }
                                            } else {
                                                lastBook = data
                                                this@BooksActivity.booksRecyclerView.adapter?.notifyDataSetChanged()
                                            }
                                        }
                                    } else showToast("No Books Found")
                                }
                            })
                    }
                }
            })
        }
    }

    private fun showDeptChooserDialog() {
        AlertDialog.Builder(this)
            .setTitle("Select Department")
            .setSingleChoiceItems(getDeptList().toTypedArray(), -1) { _, which ->
                selectedDept = which
            }
            .setPositiveButton("SELECT") { _, _ ->
                if (selectedDept != -1) {
                    getBooks()
                } else {
                    if (booksMyDeptChip.isChecked) {
                        booksMyDeptChip.isChecked = false
                    }
                    if (booksDeptChip.isChecked) {
                        booksDeptChip.isChecked = false
                    }
                    filterSelected = -1
                    showErrorToast("Select Valid Department")
                }
            }
            .setNegativeButton("CANCEL") { _, _ ->
                if (booksMyDeptChip.isChecked) {
                    booksMyDeptChip.isChecked = false
                }
                if (booksDeptChip.isChecked) {
                    booksDeptChip.isChecked = false
                }
                filterSelected = -1
            }
            .show()
    }

//    private fun getLatestBooks() {
//        val booksRef = firebaseDB.child("books")
//        val pagingConfig = PagedList.Config.Builder()
//            .setEnablePlaceholders(false)
//            .setPrefetchDistance(8)
//            .setPageSize(4)
//            .build()
//        val pagingOptions =
//            DatabasePagingOptions.Builder<Book>()
//                .setLifecycleOwner(this)
//                .setQuery(booksRef, pagingConfig, Book::class.java)
//                .build()
//
//        val booksAdapter = object : FirebaseRecyclerPagingAdapter<Book, BookVH>(pagingOptions) {
//
//
//            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookVH {
//                return BookVH(
//                    LayoutInflater.from(parent.context)
//                        .inflate(R.layout.layout_item_book, parent, false)
//                )
//            }
//
//            @SuppressLint("SetTextI18n")
//            override fun onBindViewHolder(holder: BookVH, position: Int, model: Book) {
//                holder.bookName.text = model.name
//                holder.authorEd.text = "${model.author} • ${model.edition}"
//                holder.rootView.openOnClick(BookViewActivity::class.java)
//            }
//
//            override fun onLoadingStateChanged(state: LoadingState) {
//                if (state == LoadingState.LOADED) {
//                    if (booksSRL.isRefreshing) {
//                        booksSRL.isRefreshing = false
//                    }
//                }
//            }
//        }
//        booksRecyclerView.layoutManager = LinearLayoutManager(this)
//        booksRecyclerView.adapter = booksAdapter
//    }

//    class BookVH(view: View) : RecyclerView.ViewHolder(view) {
//        val rootView: MaterialCardView = itemView.findViewById(R.id.bookItemRootView)
//        val bookName: MaterialTextView = itemView.findViewById(R.id.bookItemBookNameView)
//        val authorEd: MaterialTextView = itemView.findViewById(R.id.bookItemAuthorEdView)
//    }

}