package com.example.kodeofbook.book_test.introduction.screens.notes // Пакет экрана заметок

import androidx.compose.foundation.layout.* // Инструменты разметки
import androidx.compose.foundation.lazy.LazyColumn // Оптимизированный список
import androidx.compose.foundation.lazy.items // Итератор для списка
import androidx.compose.material.icons.Icons // Иконки
import androidx.compose.material.icons.filled.Add // Иконка плюса
import androidx.compose.material.icons.filled.Delete // Иконка корзины
import androidx.compose.material3.* // Компоненты Material 3
import androidx.compose.runtime.* // Состояния Compose
import androidx.compose.ui.Alignment // Выравнивание
import androidx.compose.ui.Modifier // Модификаторы UI
import androidx.compose.ui.unit.dp // Единицы измерения
import com.example.kodeofbook.book_test.introduction.Bus // Шина-труба
import com.example.kodeofbook.book_test.introduction.Letter // Класс письма

/**
 * ВНЕШНОСТЬ: Экран заметок.
 * Согласно методологии, экран и его состояние — это единое целое.
 * Мы работаем с данными напрямую из объекта состояния.
 */
@Composable
fun NotesScreen() { // Функция экрана
    // Получаем доступ к своей "памяти" (состоянию) через реестр Шины
    val state = Bus.registry.getOrPut("notes") { NotesState() } as NotesState 
    var text by remember { mutableStateOf("") } // Локальное состояние только для текущего ввода в поле

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) { // Главный контейнер
        Text(text = "Мои заметки", style = MaterialTheme.typography.headlineMedium) // Заголовок
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // БЛОК ЛЕГАСИ: Отображаем данные из старого мира
        Card( // Визуальный блок
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Данные из Легаси (через Адаптер):", style = MaterialTheme.typography.labelLarge)
                // ПРЯМАЯ СВЯЗЬ: просто читаем переменную. Compose следит за ней сам.
                Text(text = state.legacyWeather, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    // Просим Главу (мозг) обновить данные о погоде
                    Bus.send(Letter("NotesChapter", "RequestWeatherFromLegacy", from = "NotesUI"))
                }) {
                    Text("Запросить погоду у Легаси")
                }
            }
        } // Конец блока легаси

        Spacer(modifier = Modifier.height(24.dp))

        // Блок создания новой заметки
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Введите текст заметки...") }
            )
            IconButton(onClick = {
                // Сигнал в Главу: добавить запись в память
                Bus.send(Letter("NotesChapter", "Add", text, from = "NotesUI"))
                text = "" // Очистка локального поля ввода
            }) {
                Icon(Icons.Default.Add, contentDescription = "Добавить")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Список заметок: используется прямая ссылка на state.notes
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(state.notes) { note ->
                ListItem(
                    headlineContent = { Text(note.content) },
                    trailingContent = {
                        IconButton(onClick = {
                            // Сигнал в Главу: удалить конкретную запись
                            Bus.send(Letter("NotesChapter", "Delete", note, from = "NotesUI"))
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Удалить")
                        }
                    }
                )
            }
        }
    } // Конец колонки
} // Конец функции экрана
