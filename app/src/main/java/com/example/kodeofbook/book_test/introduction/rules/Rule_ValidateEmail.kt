package com.example.kodeofbook.book_test.introduction.rules

/**
 * Правило: Валидация Email.
 * Атомарная переиспользуемая функция без бизнес-контекста.
 */
fun Rule_ValidateEmail(email: String): Boolean {
    return email.contains("@") && email.contains(".")
}
