package com.example.kodeofbook.book_math.screens // Пакет книги расчетов

import androidx.compose.foundation.layout.* // Разметка
import androidx.compose.material3.* // Компоненты
import androidx.compose.runtime.* // Состояния
import androidx.compose.ui.Alignment // Выравнивание
import androidx.compose.ui.Modifier // Модификаторы
import androidx.compose.ui.unit.dp // Единицы
import com.example.kodeofbook.book_test.introduction.* // База

/**
 * ВНЕШНОСТЬ: Экран расчетов.
 * Согласно методологии, экран и состояние — это одно целое.
 * Здесь мы только рисуем UI, обращаясь к памяти (MathState).
 */
@Composable
fun MathScreen() { 
    // Получаем свою память из реестра Шины
    val state = Bus.registry.getOrPut("BookMath/State") { MathState() } as MathState

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp), 
        verticalArrangement = Arrangement.Center, 
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("КНИГА: СЛОЖНЫЕ РАСЧЕТЫ", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))
        
        // Поля ввода с прямой связью с MathState
        TextField(value = state.input1, onValueChange = { state.input1 = it }, label = { Text("Число A") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = state.input2, onValueChange = { state.input2 = it }, label = { Text("Число B") })
        
        Spacer(modifier = Modifier.height(20.dp))
        Text("Результат: ${state.result}", style = MaterialTheme.typography.titleLarge)
        
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { 
            // Шлем письмо своей главе по полному адресу для выполнения расчета
            Bus.send(Letter(target = "BookMath/MathChapter", action = "Calculate", from = "BookMath/UI")) 
        }) {
            Text("Посчитать")
        }
    }
}
