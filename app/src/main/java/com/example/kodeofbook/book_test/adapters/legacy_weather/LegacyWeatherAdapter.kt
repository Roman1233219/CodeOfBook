package com.example.kodeofbook.book_test.adapters.legacy_weather // Пакет адаптера

import com.example.kodeofbook.book_test.introduction.BaseChapter // База глав
import com.example.kodeofbook.book_test.introduction.Bus // Шина-труба
import com.example.kodeofbook.book_test.introduction.Letter // Класс письма
import com.example.kodeofbook.book_test.introduction.rules.Rule_Logger // Логирование

/**
 * Адаптер для Легаси-монолита.
 * Он переводит сигналы из современного мира Книги в вызовы старого кода и обратно.
 */
class LegacyWeatherAdapter : BaseChapter("LegacyWeatherAdapter") { // Адаптер как Глава

    override fun invoke(l: Letter) { // Обработка входящих писем
        Rule_Logger("LegacyAdapter", "Получен сигнал: ${l.action}")
        
        when (l.action) { // Проверка команды
            "FetchWeather" -> { // Если просят легаси-код загрузить погоду
                LegacyWeatherController.fetchWeatherDirectly { result -> // Вызов монолитного контроллера
                    Rule_Logger("LegacyAdapter", "Легаси ответил: $result. Шлю ответ обратно.")
                    // Отправляем результат тому, кто просил (динамическая нить)
                    Bus.send(Letter(target = l.from, action = "WeatherResult", data = result, from = id))
                }
            }
            "NavigateToAuth" -> { // Сигнал из легаси на переход (внутренний мостик)
                Rule_Logger("LegacyAdapter", "Легаси просит перейти на экран входа.")
                Bus.send(Letter(target = "Book", action = "OpenScreen", data = "auth", from = id))
            }
        }
    }
}
