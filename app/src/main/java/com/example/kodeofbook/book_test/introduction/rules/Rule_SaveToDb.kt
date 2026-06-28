package com.example.kodeofbook.book_test.introduction.rules // Пакет правил введения

import android.content.Context // Импорт констант контекста

/**
 * Правило: Сохранение данных в локальное хранилище.
 */
fun Rule_SaveToDb(key: String, data: String) { // Принимает ключ и строку данных
    val context = Rule_Context.app ?: return // Берем глобальный контекст приложения или выходим
    val prefs = context.getSharedPreferences("book_storage", Context.MODE_PRIVATE) // Открываем файл настроек
    prefs.edit().putString(key, data).apply() // Записываем данные асинхронно
    Rule_Logger("Storage", "Данные по ключу $key сохранены в закладку.") // Логируем успех
} // Конец правила
