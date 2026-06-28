package com.example.kodeofbook.book_math.screens // Пакет состояния расчетов

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.kodeofbook.book_math.introduction.rules.Rule_TTL // Локальное правило ТТЛ
import com.example.kodeofbook.book_test.introduction.StateScreenClass
import com.example.kodeofbook.book_test.introduction.rules.Rule_LoadFromDb
import com.example.kodeofbook.book_test.introduction.rules.Rule_SaveToDb

/**
 * СОСТОЯНИЕ: Память книги расчетов.
 * Реализует автономную физику закладок и времени жизни.
 */
class MathState : StateScreenClass("BookMath/State") {
    var input1 by mutableStateOf("") // Число А
    var input2 by mutableStateOf("") // Число Б
    var result by mutableStateOf("0") // Итог вычисления

    init {
        restore() // Восстанавливаем закладку при рождении
        // Запускаем локальный таймер Книги Математики (например, на 3 минуты)
        Rule_TTL(id, 180_000) 
    }

    override fun archive() {
        // Сохраняем состояние в "физическую" память
        Rule_SaveToDb("$id/input1", input1)
        Rule_SaveToDb("$id/input2", input2)
        Rule_SaveToDb("$id/result", result)
    }

    override fun restore() {
        // Пробуем найти закладки
        input1 = Rule_LoadFromDb("$id/input1") ?: ""
        input2 = Rule_LoadFromDb("$id/input2") ?: ""
        result = Rule_LoadFromDb("$id/result") ?: "0"
    }
}
