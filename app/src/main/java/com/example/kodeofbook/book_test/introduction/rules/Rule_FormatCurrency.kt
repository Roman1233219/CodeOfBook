package com.example.kodeofbook.book_test.introduction.rules // Пакет для правил введения

import java.text.NumberFormat // Импорт форматтера чисел
import java.util.Locale // Импорт инструментов локализации

/**
 * Правило: Форматирование валюты.
 * Превращает Double в красивую строку с символом рубля.
 */
fun Rule_FormatCurrency(amount: Double): String { // Функция правила, принимает сумму
    val locale = Locale.Builder().setLanguage("ru").setRegion("RU").build() // Создаем локаль для России
    val format = NumberFormat.getCurrencyInstance(locale) // Получаем экземпляр форматтера валюты
    return format.format(amount) // Форматируем число в строку и возвращаем результат
} // Конец правила
