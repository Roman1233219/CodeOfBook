package com.example.kodeofbook.book_test.introduction.screens.profile // Пакет состояния профиля

import com.example.kodeofbook.book_test.introduction.StateScreenClass // Базовый класс состояния
import com.example.kodeofbook.book_test.introduction.rules.Rule_LoadFromDb // Правило загрузки
import com.example.kodeofbook.book_test.introduction.rules.Rule_SaveToDb // Правило сохранения
import kotlinx.coroutines.flow.MutableStateFlow // Реактивный поток данных

// Класс состояния профиля, наследуется от базы с ID "profile"
class ProfileState : StateScreenClass("profile") { // Передаем ID в конструктор базы
    val title = "Результат расчета" // Заголовок экрана
    
    // Поля для отображения чисел и суммы
    val num1 = MutableStateFlow("") // Первое число из прошлого экрана
    val num2 = MutableStateFlow("") // Второе число из прошлого экрана
    val sum = MutableStateFlow("") // Результат сложения

    // Поле для данных о погоде (оставляем для примера)
    val weather = MutableStateFlow("Ожидание данных...") 

    val backButtonText = "Назад к вводу" // Текст кнопки возврата

    init { // Конструктор
        restore() // Восстанавливаем данные при создании объекта
    } // Конец init

    override fun archive() { // Метод сохранения "закладки"
        val data = "${num1.value}|${num2.value}|${sum.value}|${weather.value}" // Собираем все данные в строку
        Rule_SaveToDb("profile_data", data) // Сохраняем в хранилище
    } // Конец archive

    override fun restore() { // Метод восстановления
        val saved = Rule_LoadFromDb("profile_data") ?: return // Читаем или выходим
        val parts = saved.split("|") // Разбиваем строку
        if (parts.size >= 4) { // Если все части на месте
            num1.value = parts[0] // Восстанавливаем число 1
            num2.value = parts[1] // Восстанавливаем число 2
            sum.value = parts[2] // Восстанавливаем сумму
            weather.value = parts[3] // Восстанавливаем погоду
        } // Конец проверки
    } // Конец restore
} // Конец класса ProfileState
