package com.example.books_v1_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ChatActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val trash = findViewById<Button>(R.id.chat_trash)
        trash.setOnClickListener{
            val intent = Intent(this,TrashActivity::class.java)
            startActivity(intent)
        }
        val home = findViewById<Button>(R.id.chat_home)
        home.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }
    }
}