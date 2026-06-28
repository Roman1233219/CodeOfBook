package com.example.kodeofbook.book_network.screens // Пакет состояния книги сети

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.kodeofbook.book_network.introduction.rules.Rule_TTL // Локальное правило ТТЛ книги Сети
import com.example.kodeofbook.book_test.introduction.StateScreenClass
import com.example.kodeofbook.book_test.introduction.rules.Rule_LoadFromDb
import com.example.kodeofbook.book_test.introduction.rules.Rule_SaveToDb

/**
 * СОСТОЯНИЕ: Память книги сетевых запросов.
 * Реализует автономную физику закладок и времени жизни.
 */
class NetworkState : StateScreenClass("BookNetwork/State") { 
    var response by mutableStateOf("Ожидание команды...") // Текущий ответ от сети
    var lastUrl by mutableStateOf("https://wttr.in/Moscow?format=3") // Последний адрес

    init {
        restore() // Восстанавливаем закладку при рождении
        // Запускаем локальный таймер Книги Сети (например, на 10 минут)
        Rule_TTL(id, 600_000) 
    }

    override fun archive() {
        // Сохраняем сетевые данные
        Rule_SaveToDb("$id/response", response)
        Rule_SaveToDb("$id/lastUrl", lastUrl)
    }

    override fun restore() {
        // Загружаем из памяти
        response = Rule_LoadFromDb("$id/response") ?: "Ожидание команды..."
        lastUrl = Rule_LoadFromDb("$id/lastUrl") ?: "https://wttr.in/Moscow?format=3"
    }
}
