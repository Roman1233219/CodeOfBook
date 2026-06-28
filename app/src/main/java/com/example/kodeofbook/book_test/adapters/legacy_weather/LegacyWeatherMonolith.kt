package com.example.kodeofbook.book_test.adapters.legacy_weather // Пакет легаси-зоны

import androidx.compose.foundation.layout.* // Разметка
import androidx.compose.material3.* // Компоненты
import androidx.compose.runtime.* // Состояния
import androidx.compose.ui.Alignment // Выравнивание
import androidx.compose.ui.Modifier // Модификаторы
import androidx.compose.ui.unit.dp // Единицы
import kotlinx.coroutines.Dispatchers // Потоки
import kotlinx.coroutines.launch // Корутины
import kotlinx.coroutines.withContext // Переключение потоков
import java.net.URL // Сеть

/**
 * Легаси Монолит: Всё в одном файле.
 * Здесь перемешаны UI, бизнес-логика и прямые запросы в сеть.
 * Это пример кода, который мы изолируем.
 */
@Composable
fun LegacyWeatherScreen(onGoToAuth: () -> Unit) { // Экран-монолит
    var weatherResult by remember { mutableStateOf("Нажми кнопку...") } // Локальное состояние
    val scope = rememberCoroutineScope() // Область для корутин

    Column( // Весь UI прямо здесь
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("ЛЕГАСИ МОНИЛИТ (ПОГОДА)", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))
        
        Text(weatherResult) // Вывод результата
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Button(onClick = { // Прямая логика в кнопке
            scope.launch {
                weatherResult = "Загрузка..."
                try {
                    val result = withContext(Dispatchers.IO) { // Прямой сетевой вызов
                        URL("https://wttr.in/Moscow?format=3").readText()
                    }
                    weatherResult = result
                } catch (e: Exception) {
                    weatherResult = "Ошибка сети!"
                }
            }
        }) {
            Text("УЗНАТЬ ПОГОДУ (Legacy Call)")
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Button(onClick = onGoToAuth) { // Кнопка возврата
            Text("Вернуться к Входу")
        }
    }
}

/**
 * Объект-синглтон для программного взаимодействия с легаси (имитация).
 */
object LegacyWeatherController {
    var lastKnownWeather = "Нет данных"

    // Метод, который может вызвать Глава через Адаптер
    fun fetchWeatherDirectly(callback: (String) -> Unit) {
        // Имитация старого метода с колбэком
        Thread {
            try {
                val result = URL("https://wttr.in/London?format=3").readText()
                lastKnownWeather = result
                callback(result)
            } catch (e: Exception) {
                callback("Error legacy fetch")
            }
        }.start()
    }
}
