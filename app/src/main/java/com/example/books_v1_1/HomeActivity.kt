package com.example.books_v1_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.log.LoginActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private lateinit var bookList: MutableList<Book>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val exit = findViewById<Button>(R.id.button_exit)
        exit.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        val trash = findViewById<Button>(R.id.home_trash)
        trash.setOnClickListener{
            val intent = Intent(this,TrashActivity::class.java)
            startActivity(intent)
        }

        val chat = findViewById<Button>(R.id.home_chat)
        chat.setOnClickListener{
            val intent = Intent(this,ChatActivity::class.java)
            startActivity(intent)
        }


        // Настройте RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        bookList = mutableListOf()
        bookAdapter = BookAdapter(bookList)
        recyclerView.adapter = bookAdapter

        // Получите данные из базы данных и добавьте их в список bookList
        val booksRef = FirebaseDatabase.getInstance().getReference("Books")
        booksRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                bookList.clear() // Очистите список перед добавлением новых данных

                // Проходим по всем дочерним элементам
                for (bookSnapshot in dataSnapshot.children) {
                    // Получаем значения полей книги
                    val about = ""+bookSnapshot.child("About").getValue(String::class.java)
                    val author = "Автор: "+bookSnapshot.child("Author").getValue(String::class.java)
                    val cost = "Цена: "+bookSnapshot.child("Cost").getValue(Int::class.java)
                    val name =  bookSnapshot.child("Name").getValue(String::class.java)
                    val year = "Год издания: "+bookSnapshot.child("Year").getValue(Int::class.java)

                    // Создаем объект Book и добавляем его в список
                    // Создаем объект Book и добавляем его в список
                    val book = Book(name.toString(), author.toString(), about.toString(), cost, year)
                    bookList.add(book)
                }

// Уведомляем адаптер об изменении данных
                bookAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
// Обработка ошибок при чтении данных
            }
        })
    }
}