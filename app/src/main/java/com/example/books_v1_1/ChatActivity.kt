package com.example.books_v1_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.books_v1_1.Adapter.ChatAdapter
import com.example.books_v1_1.Models.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity: AppCompatActivity() {
    private lateinit var userId: String
    private lateinit var userName: String
    private lateinit var chatReference: DatabaseReference
    private lateinit var messageAdapter: ChatAdapter
    private val messageList: MutableList<Message> = mutableListOf()
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

        userId = intent.getStringExtra("userId") ?: ""
        userName = intent.getStringExtra("userName") ?: ""

        // Инициализация RecyclerView и адаптера для отображения сообщений
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewChat)
        val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        messageAdapter = ChatAdapter(messageList, currentUserUid)
        recyclerView.adapter = messageAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        chatReference = FirebaseDatabase.getInstance().reference.child("messages")

        // Получение списка сообщений из Firebase
        chatReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (messageSnapshot in snapshot.children) {
                    val message = messageSnapshot.getValue(Message::class.java)
                    if (message != null) {
                        messageList.add(message)
                    }
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ChatActivity, "Ошибка при получении сообщений: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
        val btnSendMessage = findViewById<Button>(R.id.btnSend)
        val etMessage = findViewById<EditText>(R.id.messageField)
        btnSendMessage.setOnClickListener {
            val messageText = etMessage.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val messageKey = chatReference.push().key
                if (messageKey != null) {
                    val message = Message(messageKey, userId, userName, messageText, System.currentTimeMillis())
                    chatReference.child(messageKey).setValue(message)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Сброс поля ввода после отправки сообщения
                                etMessage.text.clear()
                            } else {
                                Toast.makeText(this@ChatActivity, "Ошибка при отправке сообщения", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
    }
}