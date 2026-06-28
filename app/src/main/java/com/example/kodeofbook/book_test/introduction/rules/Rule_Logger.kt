package com.example.kodeofbook.book_test.introduction.rules // Пакет для правил введения

import android.util.Log // Импорт стандартного логера Android

/**
 * Правило: Логирование.
 * Обёртка над системным логом для единообразного вывода событий "Книги".
 */
fun Rule_Logger(tag: String, message: String) { // Функция правила, принимает тег и текст сообщения
    Log.d("BookLog_$tag", message) // Вывод в системный Logcat с префиксом BookLog_ для удобной фильтрации
} // Конец функции Rule_Logger
