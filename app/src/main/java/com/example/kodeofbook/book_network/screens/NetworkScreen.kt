package com.example.kodeofbook.book_network.screens // Пакет книги сети

import androidx.compose.foundation.layout.* // Разметка
import androidx.compose.material3.* // Компоненты
import androidx.compose.runtime.* // Состояние
import androidx.compose.ui.Alignment // Выравнивание
import androidx.compose.ui.Modifier // Модификаторы
import androidx.compose.ui.unit.dp // Единицы
import com.example.kodeofbook.book_test.introduction.* // База

/**
 * ВНЕШНОСТЬ: Экран сетевых запросов.
 * Состояние NetworkState берется из отдельного файла согласно методологии.
 */
@Composable
fun NetworkScreen() { // Глаза книги
    // Получаем свою память из реестра Шины
    val state = Bus.registry.getOrPut("BookNetwork/State") { NetworkState() } as NetworkState

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp), 
        verticalArrangement = Arrangement.Center, 
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("КНИГА: СЕТЕВЫЕ ЗАПРОСЫ", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))
        
        // Прямое отображение ответа из состояния
        Text(state.response)
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Button(onClick = { 
            // Шлем письмо своей главе по полному адресу
            Bus.send(Letter(target = "BookNetwork/Chapter", action = "ExecuteRequest", from = "BookNetwork/UI"))
        }) {
            Text("Сделать запрос")
        }
    }
}
