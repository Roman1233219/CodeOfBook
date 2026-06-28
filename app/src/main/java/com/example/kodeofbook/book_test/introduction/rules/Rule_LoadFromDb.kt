package com.example.kodeofbook.book_test.introduction.rules // Пакет правил введения

import android.content.Context // Импорт констант контекста

/**
 * Правило: Загрузка данных из локального хранилища.
 */
fun Rule_LoadFromDb(key: String): String? { // Принимает ключ, возвращает строку или null
    val context = Rule_Context.app ?: return null // Берем глобальный контекст или выходим
    val prefs = context.getSharedPreferences("book_storage", Context.MODE_PRIVATE) // Открываем файл
    val data = prefs.getString(key, null) // Читаем данные
    
    if (data != null) {
        Rule_Logger("Storage", "Закладка для $key найдена и загружена.") // Логируем успех
    } else {
        Rule_Logger("Storage", "Закладка для $key не найдена.") // Логируем отсутствие
    }
    
    return data // Возвращаем результат
} // Конец правила
