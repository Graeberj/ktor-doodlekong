package com.plcoding.util

import com.plcoding.data.models.ChatMessage

fun ChatMessage.matchesWord(word: String): Boolean{
    return message.toLowerCase().trim() == word.toLowerCase().trim()
}