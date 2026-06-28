package com.example.kodeofbook.book_network // Пакет книги сети

import com.example.kodeofbook.book_network.introduction.rules.Rule_NetworkCheck // Локальное правило
import com.example.kodeofbook.book_test.introduction.* // Глобальный фундамент
import com.example.kodeofbook.book_test.introduction.rules.Rule_HttpGet // Глобальное правило сети
import com.example.kodeofbook.book_test.introduction.rules.Rule_Logger // Логирование
import kotlinx.coroutines.* // Корутины

class NetworkChapter : BaseChapter("BookNetwork/Chapter") { // Полный адрес главы
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun invoke(l: Letter) {
        Rule_Logger("BookNetwork", "Приказ: ${l.action}")
        when (l.action) {
            "ExecuteRequest" -> {
                scope.launch {
                    val state = Bus.registry["BookNetwork/State"] as? com.example.kodeofbook.book_network.screens.NetworkState
                    state?.response = "Проверка связи..."
                    
                    if (Rule_NetworkCheck("wttr.in")) { // Используем локальное правило
                        state?.response = "Загрузка данных..."
                        val result = withContext(Dispatchers.IO) { Rule_HttpGet("https://wttr.in/Moscow?format=3") }
                        state?.response = result
                    }
                }
            }
        }
    }
}
