package com.example.kodeofbook.book_test.chapters.notes // Пакет главы заметок

import com.example.kodeofbook.book_test.introduction.BaseChapter // База глав
import com.example.kodeofbook.book_test.introduction.Bus // Шина-труба
import com.example.kodeofbook.book_test.introduction.Letter // Класс письма
import com.example.kodeofbook.book_test.introduction.rules.NoteEntity // Сущность заметки
import com.example.kodeofbook.book_test.introduction.rules.Rule_Logger // Логирование
import com.example.kodeofbook.book_test.introduction.rules.Rule_NotesDb // Физика БД
import com.example.kodeofbook.book_test.introduction.screens.notes.NotesState // Состояние экрана
import kotlinx.coroutines.* // Инструменты асинхронности

/**
 * Глава: Заметки. 
 * Это "Мозг" экрана заметок. Он не знает как рисовать UI, он знает ЧТО делать.
 */
class NotesChapter : BaseChapter("NotesChapter") { // ID для поиска в Шине
    // Личный помощник главы для фоновых задач (база данных)
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job) 

    init { // При создании Главы (рождении сюжета)
        observeNotes() // Начинаем следить за изменениями в базе данных
    } // Конец init

    override fun invoke(l: Letter) { // Точка принятия решений
        Rule_Logger("NotesChapter", "Сюжетный ход: [${l.from}] -> ${l.action}")
        
        when (l.action) { // Выбор действия по "сюжету"
            "Add" -> { // Если пользователь хочет добавить запись
                val text = l.data as? String ?: ""
                if (text.isNotBlank()) {
                    scope.launch(Dispatchers.IO) { // Уходим в поток "физики" (ввод-вывод)
                        Rule_NotesDb.dao().insert(NoteEntity(title = "Заметка", content = text)) 
                    }
                }
            } // Конец Add
            
            "Delete" -> { // Если нужно удалить запись
                val note = l.data as? NoteEntity ?: return
                scope.launch(Dispatchers.IO) { 
                    Rule_NotesDb.dao().delete(note) 
                }
            } // Конец Delete

            "RequestWeatherFromLegacy" -> { // Связь эпох: запрос к Легаси-адаптеру
                Rule_Logger("NotesChapter", "Запрос к старому миру за погодой.")
                // Шлем письмо, указывая себя (id) как адрес для ответа
                Bus.send(Letter(target = "LegacyWeatherAdapter", action = "FetchWeather", from = id))
            } // Конец RequestWeatherFromLegacy

            "WeatherResult" -> { // Результат из Легаси получен
                val weather = l.data as? String ?: "Ошибка"
                // Находим свою "память" (State) и обновляем её напрямую
                (Bus.registry["notes"] as? NotesState)?.let { it.legacyWeather = weather }
            } // Конец WeatherResult

            "Close" -> { // Сюжетный ход: закрытие раздела
                finish() // Глава уничтожает свои процессы и уходит из Шины
            }
        } // Конец when
    } // Конец invoke

    private fun observeNotes() { // Наблюдение за реальностью (БД)
        scope.launch {
            Rule_NotesDb.dao().getAllNotes().collect { list ->
                // Мгновенное обновление памяти экрана (прямая связь)
                (Bus.registry["notes"] as? NotesState)?.notes?.apply {
                    clear()
                    addAll(list)
                }
            }
        }
    } // Конец наблюдения

    private fun finish() { // Метод окончательного завершения
        job.cancel() // Останавливаем все фоновые процессы Главы
        Bus.registry.remove(id) // Выписываемся из Шины (освобождаем ОЗУ)
        Rule_Logger("NotesChapter", "Глава завершена и удалена из реестра.")
    } // Конец finish
} // Конец класса
