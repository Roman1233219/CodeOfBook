package com.example.kodeofbook.book_test.introduction.screens.notes // Пакет состояния экрана заметок

import androidx.compose.runtime.getValue // Импорт для удобного чтения через by
import androidx.compose.runtime.mutableStateListOf // Списки Compose
import androidx.compose.runtime.mutableStateOf // Прямое состояние Compose
import androidx.compose.runtime.setValue // Импорт для удобной записи через by
import com.example.kodeofbook.book_test.introduction.StateScreenClass // База
import com.example.kodeofbook.book_test.introduction.rules.NoteEntity // Сущность

/**
 * СОСТОЯНИЕ: Внутренняя память Экрана.
 * В рамках методологии — это часть самого Экрана.
 */
class NotesState : StateScreenClass("notes") {
    // Список заметок с прямой связью
    val notes = mutableStateListOf<NoteEntity>()
    
    // Прямая переменная состояния (не поток!). 
    // Изменение этой переменной мгновенно перерисовывает экран.
    var legacyWeather by mutableStateOf("Погода еще не запрашивалась")
} // Конец класса
