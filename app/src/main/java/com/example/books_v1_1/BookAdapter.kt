package com.example.books_v1_1

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.database.*
import org.w3c.dom.Text

class BookAdapter(private val bookList: List<Book>) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val authorTextView: TextView = itemView.findViewById(R.id.authorTextView)
        val yearTextView: TextView = itemView.findViewById(R.id.yearTextViev)
        val costTextView: TextView = itemView.findViewById(R.id.costTextViev)
        val aboutTextView: TextView = itemView.findViewById(R.id.aboutTextViev)


        // Добавьте остальные TextView для отображения других полей
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_book, parent, false)

        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = bookList[position]
        holder.nameTextView.text = book.name
        holder.authorTextView.text = book.author
        holder.yearTextView.text = book.year.toString()
        holder.costTextView.text = book.cost.toString()
        holder.aboutTextView.text = book.about
        // Установите остальные значения в TextView на основе полей Book
    }

    override fun getItemCount(): Int {



        return bookList.size
    }
}
