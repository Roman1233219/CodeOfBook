package com.example.kodeofbook.book_test.introduction.screens.recovery // Пакет экрана восстановления

import androidx.compose.foundation.layout.* // Импорт инструментов разметки
import androidx.compose.material3.Button // Импорт кнопки
import androidx.compose.material3.Text // Импорт текста
import androidx.compose.runtime.Composable // Пометка UI-функции
import androidx.compose.ui.Alignment // Инструмент выравнивания
import androidx.compose.ui.Modifier // Инструмент настройки UI
import androidx.compose.ui.unit.dp // Единицы измерения отступов
import com.example.kodeofbook.book_test.introduction.Bus // Импорт Шины-трубы
import com.example.kodeofbook.book_test.introduction.Letter // Импорт класса письма

@Composable // UI-компонент
fun RecoveryScreen() { // Функция экрана восстановления пароля
    // Получаем или создаем состояние экрана из реестра Шины
    val state = Bus.registry.getOrPut("recovery") { RecoveryState() } as RecoveryState // Поиск объекта по ключу

    Column( // Вертикальный стек элементов
        modifier = Modifier.fillMaxSize(), // Занимаем весь экран
        verticalArrangement = Arrangement.Center, // Центрируем по вертикали
        horizontalAlignment = Alignment.CenterHorizontally // Центрируем по горизонтали
    ) { // Начало контента
        Text(text = state.title) // Показываем заголовок из состояния
        Spacer(modifier = Modifier.height(16.dp)) // Разделитель
        
        Button(onClick = { // Кнопка возврата к авторизации
            Bus.send(Letter(target = "RecoveryChapter", action = "BackToAuth")) // Сигнал главе восстановления
        }) { // Контент кнопки
            Text(text = state.backToAuthText) // Текст кнопки из состояния
        } // Конец кнопки
        
        Spacer(modifier = Modifier.height(8.dp)) // Разделитель
        
        Button(onClick = { // Кнопка возврата в профиль
            Bus.send(Letter(target = "RecoveryChapter", action = "BackToProfile")) // Сигнал главе восстановления
        }) { // Контент кнопки
            Text(text = state.backToProfileText) // Текст кнопки из состояния
        } // Конец кнопки
        
        Spacer(modifier = Modifier.height(8.dp)) // Разделитель
        
        Button(onClick = { // Кнопка завершения процесса
            Bus.send(Letter(target = "RecoveryChapter", action = "Finish")) // Сигнал главе завершить процесс
        }) { // Контент кнопки
            Text(text = state.finishText) // Текст кнопки из состояния
        } // Конец кнопки
    } // Конец колонки
} // Конец функции экрана
