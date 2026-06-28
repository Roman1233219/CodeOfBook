package com.example.kodeofbook.book_test.introduction.screens.auth // Пакет состояния авторизации

import com.example.kodeofbook.book_test.introduction.StateScreenClass // База для состояний
import com.example.kodeofbook.book_test.introduction.rules.Rule_LoadFromDb // Правило загрузки
import com.example.kodeofbook.book_test.introduction.rules.Rule_SaveToDb // Правило сохранения
import kotlinx.coroutines.flow.MutableStateFlow // Реактивные потоки

class AuthState : StateScreenClass("auth") { // Состояние первого экрана
    val title = "Ввод чисел" // Заголовок
    
    val num1 = MutableStateFlow("") // Хранилище для первого числа
    val num2 = MutableStateFlow("") // Хранилище для второго числа
    val buttonText = "Рассчитать и перейти" // Текст кнопки

    init { // Инициализатор объекта
        restore() // При рождении пытаемся восстановить данные из закладки
    } // Конец init

    override fun archive() { // Метод сохранения "закладки" перед смертью
        val data = "${num1.value}|${num2.value}" // Упаковываем данные в строку
        Rule_SaveToDb("auth_data", data) // Сохраняем строку в локальное хранилище
    } // Конец archive

    override fun restore() { // Метод восстановления данных при рождении
        val saved = Rule_LoadFromDb("auth_data") ?: return // Читаем данные или выходим, если пусто
        val parts = saved.split("|") // Разбиваем строку на части
        if (parts.size == 2) { // Если данных достаточно
            num1.value = parts[0] // Восстанавливаем первое число
            num2.value = parts[1] // Восстанавливаем второе число
        } // Конец проверки
    } // Конец restore
} // Конец AuthState
