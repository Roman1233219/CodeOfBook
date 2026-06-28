package com.example.kodeofbook.book_math // Пакет книги расчетов

import com.example.kodeofbook.book_math.screens.MathState // Память
import com.example.kodeofbook.book_test.introduction.* // База
import com.example.kodeofbook.book_test.introduction.rules.Rule_Logger // Логирование

class MathChapter : BaseChapter("BookMath/MathChapter") {

    override fun invoke(l: Letter) {
        Rule_Logger("BookMath", "Логика расчета: ${l.action}")
        when (l.action) {
            "Calculate" -> {
                val state = Bus.registry["BookMath/State"] as? MathState ?: return
                val a = state.input1.toIntOrNull() ?: 0
                val b = state.input2.toIntOrNull() ?: 0
                state.result = (a + b).toString()
                Rule_Logger("BookMath", "Результат: $a + $b = ${state.result}")
            }
        }
    }
}
