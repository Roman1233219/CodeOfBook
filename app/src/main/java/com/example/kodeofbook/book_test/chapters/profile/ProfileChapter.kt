package com.example.kodeofbook.book_test.chapters.profile // Пакет главы профиля

import com.example.kodeofbook.book_test.introduction.BaseChapter // База глав
import com.example.kodeofbook.book_test.introduction.Bus // Шина-труба
import com.example.kodeofbook.book_test.introduction.Letter // Класс письма
import com.example.kodeofbook.book_test.introduction.rules.Rule_GetWeather // Правило получения погоды
import com.example.kodeofbook.book_test.introduction.rules.Rule_Logger // Правило логирования
import com.example.kodeofbook.book_test.introduction.screens.profile.ProfileState // Состояние профиля
import kotlinx.coroutines.CoroutineScope // Область корутин
import kotlinx.coroutines.Dispatchers // Диспетчеры потоков
import kotlinx.coroutines.launch // Запуск корутины

class ProfileChapter : BaseChapter("ProfileChapter") { // Глава профиля, наследуется от базы

    override fun invoke(l: Letter) { // Обработка входящих писем
        Rule_Logger("ProfileChapter", "Глава получила приказ: ${l.action}") // Логируем входящее действие
        when (l.action) { // Проверка действия
            "Back" -> { // Если команда "Назад"
                Rule_Logger("ProfileChapter", "Сюжет: Возврат из профиля на экран авторизации") // Логируем шаг сюжета
                Bus.send(Letter("Book", "OpenScreen", "auth")) // Просим Книгу открыть экран входа
                finishProfile() // Вызываем метод завершения
            } // Конец Back
            "GoToRecovery" -> { // Если переход к восстановлению
                Rule_Logger("ProfileChapter", "Сюжет: Переход к восстановлению пароля") // Логируем шаг сюжета
                Bus.send(Letter("Book", "OpenScreen", "recovery")) // Просим Книгу открыть восстановление
            } // Конец GoToRecovery
            "LoadWeather" -> { // Если нужно загрузить погоду
                Rule_Logger("ProfileChapter", "Сюжет: Получен запрос на обновление погоды") // Логируем шаг сюжета
                loadWeatherData() // Запуск процесса загрузки
            } // Конец LoadWeather
            "Logout" -> { // Если выход из аккаунта
                Rule_Logger("ProfileChapter", "Сюжет: Выход из аккаунта") // Логируем шаг сюжета
                Bus.send(Letter("Book", "OpenScreen", "auth")) // Возвращаемся на вход
                finishProfile() // Очищаем данные профиля
            } // Конец Logout
        } // Конец when
    } // Конец invoke

    private fun loadWeatherData() { // Логика загрузки погоды
        val state = Bus.registry["profile"] as? ProfileState ?: return // Берем состояние из реестра
        Rule_Logger("ProfileChapter", "Сюжет: Запрос данных погоды для Москвы") // Логируем действие
        
        CoroutineScope(Dispatchers.Main).launch { // Запускаем задачу в главном потоке
            val result = Rule_GetWeather("Moscow") // Используем правило получения погоды
            Rule_Logger("ProfileChapter", "Сюжет: Погода получена: $result. Обновляю состояние.") // Логируем результат
            state.weather.value = result // Обновляем состояние данными
        } // Конец корутины
    } // Конец loadWeatherData

    private fun finishProfile() { // Очистка при уходе
        Rule_Logger("ProfileChapter", "Сюжет: Завершение работы главы ProfileChapter и очистка состояния") // Логируем очистку
        Bus.registry.remove(id) // Удаляем главу из реестра
        Bus.registry.remove("profile") // Удаляем само состояние профиля (данные)
    } // Конец finishProfile
} // Конец класса
