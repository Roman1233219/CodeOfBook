package com.example.kodeofbook.book_test.introduction // Пакет фундамента системы

import com.example.kodeofbook.book_test.introduction.rules.Rule_TTL // Правило времени жизни
import com.example.kodeofbook.book_test.introduction.rules.Rule_Logger // Правило логирования

/**
 * Базовый класс Состояния. Умеет жить, умирать и оставлять "закладки" (автосохранение).
 */
abstract class StateScreenClass(val id: String) { // ID совпадает с ключом в Шине

    /**
     * Запускает таймер самоуничтожения.
     */
    fun startLifeTimer(ms: Long = 300_000) { // По умолчанию 5 минут
        Rule_Logger("System", "Объект $id: Таймер жизни запущен ($ms мс)") // Лог старта
        Rule_TTL(id, ms) // Запуск автономного правила уничтожения
    } // Конец старта таймера

    /**
     * Метод-закладка: вызывается перед смертью для сохранения данных.
     * Каждое состояние само решает, ЧТО именно сохранять.
     */
    open fun archive() { 
        Rule_Logger("System", "Объект $id: Выполняется стандартная архивация (пусто).") 
    } // Конец метода архивации

    /**
     * Метод-восстановление: вызывается при рождении.
     */
    open fun restore() { 
        Rule_Logger("System", "Объект $id: Выполняется стандартное восстановление (пусто).") 
    } // Конец метода восстановления
} // Конец класса
