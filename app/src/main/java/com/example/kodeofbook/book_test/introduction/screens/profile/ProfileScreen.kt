package com.example.kodeofbook.book_test.introduction.screens.profile // Пакет экрана профиля

import androidx.compose.foundation.layout.* // Разметка
import androidx.compose.material3.* // Компоненты Material3
import androidx.compose.runtime.* // Состояния Compose
import androidx.compose.ui.Alignment // Выравнивание
import androidx.compose.ui.Modifier // Модификаторы
import androidx.compose.ui.unit.dp // Пиксели
import com.example.kodeofbook.book_test.introduction.Bus // Шина
import com.example.kodeofbook.book_test.introduction.Letter // Письмо

@Composable // UI-функция
fun ProfileScreen() { // Экран профиля с результатами расчета
    // Получаем состояние из реестра Шины
    val state = Bus.registry.getOrPut("profile") { ProfileState() } as ProfileState
    
    // Подписываемся на данные для отображения
    val n1 by state.num1.collectAsState()
    val n2 by state.num2.collectAsState()
    val s by state.sum.collectAsState()

    Column( // Вертикальный список
        modifier = Modifier.fillMaxSize().padding(16.dp), // На весь экран с отступами
        verticalArrangement = Arrangement.Center, // Центрируем по вертикали
        horizontalAlignment = Alignment.CenterHorizontally // Центрируем по горизонтали
    ) { // Начало контента
        Text(text = state.title, style = MaterialTheme.typography.headlineMedium) // Заголовок
        
        Spacer(modifier = Modifier.height(16.dp))

        // Первое поле (только для чтения или просто вывод)
        TextField(
            value = n1,
            onValueChange = {}, // Не меняем здесь
            readOnly = true,
            label = { Text("Число 1") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Второе поле
        TextField(
            value = n2,
            onValueChange = {}, // Не меняем здесь
            readOnly = true,
            label = { Text("Число 2") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Третье поле - Сумма
        TextField(
            value = s,
            onValueChange = {}, // Не меняем здесь
            readOnly = true,
            label = { Text("Сумма (Результат)") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(focusedContainerColor = MaterialTheme.colorScheme.primaryContainer)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { // Кнопка возврата
            // Отправляем сигнал главе Профиля
            Bus.send(Letter(target = "ProfileChapter", action = "Back")) 
        }) { 
            Text(text = state.backButtonText) // Текст из состояния
        }
    } // Конец колонки
} // Конец ProfileScreen
