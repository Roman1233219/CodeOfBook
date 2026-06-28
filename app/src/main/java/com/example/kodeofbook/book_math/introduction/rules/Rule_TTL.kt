package com.example.kodeofbook.book_math.introduction.rules // Локальные правила книги Математики

import com.example.kodeofbook.book_test.introduction.Bus
import com.example.kodeofbook.book_test.introduction.StateScreenClass
import com.example.kodeofbook.book_test.introduction.rules.Rule_Logger
import kotlinx.coroutines.*

/**
 * Локальное правило TTL для Книги Математики.
 * Автономно управляет временем жизни математических состояний.
 */
@OptIn(DelicateCoroutinesApi::class)
fun Rule_TTL(id: String, timeMs: Long) {
    GlobalScope.launch {
        delay(timeMs)
        val obj = Bus.registry[id]
        if (obj is StateScreenClass) {
            Rule_Logger("MathPhysics", "Книга Математики: Архивация объекта $id")
            obj.archive()
        }
        Bus.registry.remove(id)
        Rule_Logger("MathPhysics", "Книга Математики: Объект $id удален из реальности.")
    }
}
