package com.example.books_v1_1.Models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Message(
    var messageId: String = "",
    var senderId: String = "",
    var senderName: String = "",
    var messageText: String = "",
    var timestamp: Long = 0
)