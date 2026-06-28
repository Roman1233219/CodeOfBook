package com.example.kodeofbook.book_test.introduction.screens.recovery // Пакет состояния восстановления

import com.example.kodeofbook.book_test.introduction.StateScreenClass // Базовый класс состояния

// Класс состояния для экрана восстановления пароля с ID "recovery"
class RecoveryState : StateScreenClass("recovery") { // Передаем ID в конструктор базы
    val title = "Восстановление пароля" // Заголовок экрана
    val backToAuthText = "К Авторизации" // Текст кнопки возврата к входу
    val backToProfileText = "В Профиль" // Текст кнопки возврата в профиль
    val finishText = "Сбросить и Очистить все" // Текст кнопки завершения
} // Конец класса RecoveryState
