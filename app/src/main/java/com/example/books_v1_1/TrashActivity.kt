package com.example.books_v1_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TrashActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trash)

        val chat = findViewById<Button>(R.id.trash_chat)
        chat.setOnClickListener{
            val intent = Intent(this,ChatActivity::class.java)
            startActivity(intent)
        }
        val home = findViewById<Button>(R.id.trash_home)
        home.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }
    }
}