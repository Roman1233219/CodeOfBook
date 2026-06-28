package com.example.kodeofbook.book_test.introduction.rules // Пакет автономных правил мира

import com.example.kodeofbook.book_test.introduction.Bus // Импорт Шины для доступа к реестру
import com.example.kodeofbook.book_test.introduction.StateScreenClass // Импорт базы состояний для вызова архивации
import kotlinx.coroutines.* // Импорт корутин для асинхронного ожидания

/**
 * Правило TTL: Инструмент, позволяющий объекту запланировать свое удаление с предварительным сохранением.
 */
@OptIn(DelicateCoroutinesApi::class) // Разрешение на использование GlobalScope для фонового таймера
fun Rule_TTL(id: String, timeMs: Long) { // Функция принимает ID и время в миллисекундах
    GlobalScope.launch { // Запуск независимой корутины, которая живет дольше экрана
        delay(timeMs) // Ожидание истечения срока жизни
        val obj = Bus.registry[id] // Ищем объект в реестре перед удалением
        if (obj is StateScreenClass) { // Проверяем, является ли объект состоянием экрана
            Rule_Logger("System", "Объект $id готовится к удалению. Запуск архивации.") // Логируем начало сохранения
            obj.archive() // Даем объекту шанс сохранить свои данные (Закладка)
        } // Конец проверки типа
        Bus.registry.remove(id) // Прямое удаление объекта из реестра Шины (самоуничтожение)
        Rule_Logger("System", "Объект $id автономно покинул реестр по TTL.") // Логирование завершения
    } // Конец блока корутины
} // Конец функции правила
