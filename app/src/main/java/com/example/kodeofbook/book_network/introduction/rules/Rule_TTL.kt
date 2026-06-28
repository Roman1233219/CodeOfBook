package com.example.kodeofbook.book_network.introduction.rules // Локальные правила книги Сети

import com.example.kodeofbook.book_test.introduction.Bus
import com.example.kodeofbook.book_test.introduction.StateScreenClass
import com.example.kodeofbook.book_test.introduction.rules.Rule_Logger
import kotlinx.coroutines.*

/**
 * Локальное правило TTL для Книги Сети.
 * Автономно управляет временем жизни сетевых состояний.
 */
@OptIn(DelicateCoroutinesApi::class)
fun Rule_TTL(id: String, timeMs: Long) {
    GlobalScope.launch {
        delay(timeMs)
        val obj = Bus.registry[id]
        if (obj is StateScreenClass) {
            Rule_Logger("NetworkPhysics", "Книга Сети: Авто-сохранение объекта $id")
            obj.archive()
        }
        Bus.registry.remove(id)
        Rule_Logger("NetworkPhysics", "Книга Сети: Объект $id удален.")
    }
}
