package com.example.kodeofbook.book_network.introduction.rules // Правила конкретной книги

import com.example.kodeofbook.book_test.introduction.rules.Rule_Logger

/**
 * Правило: Проверка доступности сетевого узла.
 */
fun Rule_NetworkCheck(url: String): Boolean {
    Rule_Logger("BookNetwork/Rules", "Проверка связи с: $url")
    return true // Имитация: всегда доступно
}
