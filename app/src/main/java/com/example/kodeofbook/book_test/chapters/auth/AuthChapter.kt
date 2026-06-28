package com.example.kodeofbook.book_test.chapters.auth // Пакет главы авторизации

import com.example.kodeofbook.book_test.introduction.BaseChapter // База для глав
import com.example.kodeofbook.book_test.introduction.Bus // Шина-труба
import com.example.kodeofbook.book_test.introduction.Letter // Класс письма
import com.example.kodeofbook.book_test.introduction.rules.Rule_Logger // Правило логирования
import com.example.kodeofbook.book_test.introduction.screens.auth.AuthState // Состояние входа
import com.example.kodeofbook.book_test.introduction.screens.profile.ProfileState // Состояние профиля

/**
 * Глава: Авторизация. Сюжетный модуль управления вводом и переходом.
 */
class AuthChapter : BaseChapter("AuthChapter") { // Наследуемся от базы

    override fun invoke(l: Letter) { // Обработка входящих команд
        Rule_Logger("AuthChapter", "Глава получила приказ: ${l.action}") // Логируем действие
        when (l.action) { // Проверка действия
            "Login" -> { // Если нажали кнопку перехода
                // 1. Получаем состояние текущего экрана
                val authState = Bus.registry["auth"] as? AuthState 
                
                if (authState != null) { // Если состояние найдено
                    val v1 = authState.num1.value // Читаем первое число
                    val v2 = authState.num2.value // Читаем второе число
                    
                    Rule_Logger("AuthChapter", "Данные получены: $v1, $v2. Готовлю экран профиля.")

                    // 2. Создаем или берем состояние профиля
                    val profileState = Bus.registry.getOrPut("profile") { ProfileState() } as ProfileState
                    
                    // 3. Переносим данные и считаем сумму
                    profileState.num1.value = v1 // Копируем первое число
                    profileState.num2.value = v2 // Копируем второе число
                    val s = (v1.toIntOrNull() ?: 0) + (v2.toIntOrNull() ?: 0) // Считаем сумму
                    profileState.sum.value = s.toString() // Записываем результат
                    
                    Rule_Logger("AuthChapter", "Расчет завершен: $v1 + $v2 = $s")
                }

                // 4. Даем команду Книге перевернуть страницу
                Bus.send(Letter("Book", "OpenScreen", "profile"))
                
                // 5. Глава выполнила сюжетный переход и самоуничтожается
                Bus.registry.remove(id) 
            }
        }
    }
}
