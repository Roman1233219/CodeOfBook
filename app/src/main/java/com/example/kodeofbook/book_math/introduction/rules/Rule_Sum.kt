package com.example.kodeofbook.book_math.introduction.rules // Пакет правил книги расчетов

import com.example.kodeofbook.book_test.introduction.rules.Rule_Logger // Логирование из базового введения

/**
 * Правило: Безопасное сложение двух чисел.
 */
fun Rule_Sum(a: String, b: String): String {
    val num1 = a.toIntOrNull() ?: 0 // Превращаем строку в число
    val num2 = b.toIntOrNull() ?: 0 // Превращаем строку в число
    val result = num1 + num2 // Складываем
    Rule_Logger("BookMath/Rules", "Закон сложения: $num1 + $num2 = $result") // Логируем физику процесса
    return result.toString() // Возвращаем результат
}
