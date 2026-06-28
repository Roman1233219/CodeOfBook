package com.example.kodeofbook.book_test.chapters.recovery // Пакет главы восстановления

import com.example.kodeofbook.book_test.introduction.BaseChapter // База глав
import com.example.kodeofbook.book_test.introduction.Bus // Шина
import com.example.kodeofbook.book_test.introduction.Letter // Письмо
import com.example.kodeofbook.book_test.introduction.rules.Rule_Logger // Правило логирования

/**
 * Глава: Восстановление пароля. Сюжетный модуль для сброса доступа.
 */
class RecoveryChapter : BaseChapter("RecoveryChapter") { // Глава "мозг" для восстановления, ID "RecoveryChapter"

    override fun invoke(l: Letter) { // Обработка входящих сигналов (писем)
        Rule_Logger("RecoveryChapter", "Глава получила приказ: ${l.action}") // Логируем входящее действие
        when (l.action) { // Проверка действия в письме
            "BackToAuth" -> { // Если команда вернуться к входу
                Rule_Logger("RecoveryChapter", "Сюжет: Возврат к экрану авторизации") // Логируем шаг сюжета
                Bus.send(Letter("Book", "OpenScreen", "auth")) // Команда Книге открыть экран входа
                finishRecovery() // Очистка и завершение главы
            } // Конец BackToAuth
            "BackToProfile" -> { // Если команда вернуться в профиль
                Rule_Logger("RecoveryChapter", "Сюжет: Возврат к экрану профиля") // Логируем шаг сюжета
                Bus.send(Letter("Book", "OpenScreen", "profile")) // Команда Книге открыть профиль
                finishRecovery() // Очистка и завершение главы
            } // Конец BackToProfile
            "Finish" -> { // Если нажали сбросить и закончить
                Rule_Logger("RecoveryChapter", "Сюжет: Сброс пароля завершен. Переход на начало.") // Логируем шаг сюжета
                Bus.send(Letter("Book", "OpenScreen", "auth")) // Возврат на начальный экран
                finishRecovery() // Полная очистка ресурсов главы и состояния
            } // Конец Finish
        } // Конец when
    } // Конец invoke

    private fun finishRecovery() { // Вспомогательный метод завершения главы
        Rule_Logger("RecoveryChapter", "Сюжет: Самоуничтожение главы и очистка состояния recovery") // Логируем финал
        Bus.registry.remove(id) // Удаляем саму главу из реестра Шины
        Bus.registry.remove("recovery") // Удаляем состояние экрана восстановления из реестра
    } // Конец finishRecovery
} // Конец RecoveryChapter
