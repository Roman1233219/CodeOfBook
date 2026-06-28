package com.example.kodeofbook.book_test.introduction // Пакет фундамента системы

import com.example.kodeofbook.book_test.introduction.rules.Rule_Logger // Импорт правила логирования
import java.util.concurrent.ConcurrentHashMap // Импорт реестра

// Письмо: Универсальный контракт связи между главами и книгами.
data class Letter( // Структура сообщения
    val target: String, // Кому: адрес получателя
    val action: String, // Что: команда или событие
    val data: Any? = null, // Груз: параметры
    val from: String = "Unknown" // Отправитель: для отслеживания цепочки событий
) // Конец класса Letter

/**
 * Шина: Простая "труба" для доставки писем и реестр живых объектов.
 */
object Bus { // Синглтон-узел связи
    val registry = ConcurrentHashMap<String, Any>() // Карта живых объектов в памяти
    val chapters = mutableMapOf<String, (Letter) -> Any>() // Карта доступных Глав (сюжетов)

    fun send(l: Letter) { // Единственный метод доставки
        Rule_Logger("Bus", "Письмо: [${l.from}] -> [${l.target}] : ${l.action}") // Логируем маршрут
        // Поиск в реестре. Если нет — создание Главы.
        val obj = if (registry.containsKey(l.target)) registry[l.target] else create(l) 
        (obj as? BaseChapter)?.invoke(l) // Проброс письма в Главу
    } // Конец метода send

    private fun create(l: Letter) = chapters[l.target]?.invoke(l)?.also { // Рождение объекта
        registry[l.target] = it // Прописка в реестре
        Rule_Logger("Bus", "Объект ${l.target} создан.") // Лог успеха
    } // Конец метода create
} // Конец объекта Bus

abstract class BaseChapter(val id: String) { // База для всех Глав
    abstract operator fun invoke(l: Letter) // Метод обработки входящих сигналов
} // Конец BaseChapter
