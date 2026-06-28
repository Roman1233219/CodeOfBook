@file:Suppress("ClassName") // Подавляем предупреждение о стиле именования для всего файла
package com.example.kodeofbook.book_test.introduction.rules // Пакет правил введения

import android.content.Context // Импорт Android контекста
import java.lang.ref.WeakReference // Импорт слабой ссылки для защиты от утечек памяти

/**
 * Правило: Глобальный контекст.
 * Позволяет правилам (БД, Сеть) получать доступ к ресурсам системы,
 * сохраняя Главы и Состояния чистыми от прямых Android-зависимостей.
 */
object Rule_Context { // Синглтон-хранитель системного контекста
    
    // Закрытое поле со слабой ссылкой. Это предотвращает утечку памяти,
    // так как WeakReference не мешает сборщику мусора удалять контекст.
    private var _app: WeakReference<Context>? = null // Контейнер для ссылки на приложение

    // Свойство для безопасного доступа к контексту приложения
    var app: Context? // Геттер и сеттер
        get() = _app?.get() // Пытаемся достать объект из "слабой" памяти
        set(value) { // При установке нового значения (обычно в MainActivity)
            _app = if (value != null) WeakReference(value.applicationContext) else null // Сохраняем только контекст приложения
        } // Конец блока set
} // Конец объекта Rule_Context
