package com.jobayr.bcm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.jobayr.bcm.R
import com.jobayr.bcm.activities.BookViewActivity
import com.jobayr.bcm.extensions.openOnClick
import com.jobayr.bcm.models.Book

class BookAdapter(
    private val items: MutableList<Book>
) : RecyclerView.Adapter<BookAdapter.BookVH>() {

    class BookVH(view: View) : RecyclerView.ViewHolder(view) {
        val rootView: MaterialCardView = itemView.findViewById(R.id.bookItemRootView)
        val bookNameView: MaterialTextView = itemView.findViewById(R.id.bookItemBookNameView)
        val authorEdView: MaterialTextView = itemView.findViewById(R.id.bookItemAuthorEdView)
//        val deptView: MaterialTextView = itemView.findViewById(R.id.bookItemDeptView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookVH {
        return BookVH(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_book, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BookVH, position: Int) {
        holder.rootView.openOnClick(BookViewActivity::class.java)
        holder.bookNameView.text = items[position].name
        holder.authorEdView.text = "${items[position].author} • ${items[position].edition}"
    }

}