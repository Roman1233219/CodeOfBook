package com.example.kodeofbook.book_test.introduction.screens.auth // Пакет экрана авторизации

import androidx.compose.foundation.layout.* // Инструменты разметки
import androidx.compose.material3.* // Компоненты Material3 (Button, TextField, Text)
import androidx.compose.runtime.* // Состояния Compose
import androidx.compose.ui.Alignment // Выравнивание
import androidx.compose.ui.Modifier // Модификаторы
import androidx.compose.ui.unit.dp // Единицы измерения
import com.example.kodeofbook.book_test.introduction.Bus // Шина
import com.example.kodeofbook.book_test.introduction.Letter // Письмо
import com.example.kodeofbook.book_test.introduction.rules.Rule_Logger // Логирование

@Composable // UI-компонент
fun AuthScreen() { // Экран ввода чисел
    // Получаем состояние из Шины
    val state = Bus.registry.getOrPut("auth") { AuthState() } as AuthState
    
    // Подписываемся на реактивные поля ввода
    val n1 by state.num1.collectAsState()
    val n2 by state.num2.collectAsState()

    Column( // Вертикальный стек
        modifier = Modifier.fillMaxSize().padding(16.dp), // На весь экран с отступами
        verticalArrangement = Arrangement.Center, // Центр по вертикали
        horizontalAlignment = Alignment.CenterHorizontally // Центр по горизонтали
    ) {
        Text(text = state.title, style = MaterialTheme.typography.headlineMedium) // Заголовок
        
        Spacer(modifier = Modifier.height(16.dp))

        // Поле ввода для первого числа
        TextField(
            value = n1,
            onValueChange = { state.num1.value = it }, // Обновляем состояние напрямую
            label = { Text("Число 1") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Поле ввода для второго числа
        TextField(
            value = n2,
            onValueChange = { state.num2.value = it }, // Обновляем состояние напрямую
            label = { Text("Число 2") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { // Кнопка перехода
            Rule_Logger("AuthScreen", "Переход к расчету: $n1 + $n2")
            Bus.send(Letter(target = "AuthChapter", action = "Login")) // Сигнал главе
        }) {
            Text(text = state.buttonText)
        }
    }
}
