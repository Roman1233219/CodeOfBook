package com.example.kodeofbook.book_test.introduction // Пакет фундамента системы

import androidx.compose.runtime.Composable // Импорт для работы с UI-компонентами

/**
 * Реестр Экранов: Оглавление всех страниц Книги.
 * Позволяет добавлять новые экраны без изменения кода MainActivity.
 */
object ScreenRegistry { // Глобальный реестр страниц
    // Карта: Название экрана -> Функция отрисовки (Composable)
    private val screens = mutableMapOf<String, @Composable () -> Unit>()

    /**
     * Регистрация новой страницы.
     */
    fun register(name: String, screen: @Composable () -> Unit) { 
        screens[name] = screen // Добавляем экран в оглавление
    } // Конец регистрации

    /**
     * Получение страницы по названию.
     */
    @Composable
    fun Show(name: String) { 
        screens[name]?.invoke() // Вызов функции отрисовки, если экран найден
    } // Конец вызова
} // Конец реестра
