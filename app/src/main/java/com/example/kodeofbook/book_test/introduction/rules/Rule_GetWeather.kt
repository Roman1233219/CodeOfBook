package com.example.kodeofbook.book_test.introduction.rules // Пакет правил введения

/**
 * Правило: Получение погоды.
 * Использует Rule_HttpGet для получения данных с wttr.in.
 */
suspend fun Rule_GetWeather(city: String): String { // Функция правила, принимает город
    Rule_Logger("Rule_Weather", "Запуск правила: запрос погоды для $city") // Логируем запуск правила
    val url = "https://wttr.in/$city?format=3" // Формируем URL для запроса
    val result = Rule_HttpGet(url) // Вызываем правило сетевого запроса
    
    return if (result == "Error") { // Если произошла ошибка
        Rule_Logger("Rule_Weather", "Ошибка при получении погоды для $city") // Логируем ошибку
        "Не удалось загрузить погоду" // Возвращаем текст ошибки
    } else { // Если успех
        val trimmed = result.trim() // Обрезаем пробелы
        Rule_Logger("Rule_Weather", "Успех: полученная погода -> $trimmed") // Логируем успех
        trimmed // Возвращаем результат
    } // Конец условия
} // Конец правила
