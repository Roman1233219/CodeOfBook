package com.example.kodeofbook // Пакет приложения

import android.os.Bundle // Базовый класс Android
import androidx.activity.ComponentActivity // Базовая активити
import androidx.activity.compose.setContent // Привязка Compose
import androidx.activity.enableEdgeToEdge // Отрисовка на весь экран
import androidx.compose.foundation.layout.fillMaxSize // Модификатор размера
import androidx.compose.foundation.layout.padding // Модификатор отступов
import androidx.compose.material.icons.Icons // Иконки
import androidx.compose.material.icons.filled.* // Стандартный набор иконок
import androidx.compose.material3.* // Компоненты Material 3
import androidx.compose.runtime.getValue // Чтение состояния
import androidx.compose.runtime.mutableStateOf // Создание состояния
import androidx.compose.runtime.setValue // Запись состояния
import androidx.compose.ui.Modifier // Модификаторы UI
import com.example.kodeofbook.book_math.MathChapter // Глава расчетов
import com.example.kodeofbook.book_math.screens.MathScreen // Экран расчетов
import com.example.kodeofbook.book_network.NetworkChapter // Глава сети
import com.example.kodeofbook.book_network.screens.NetworkScreen // Экран сети
import com.example.kodeofbook.book_test.adapters.legacy_weather.LegacyWeatherAdapter // Адаптер для легаси
import com.example.kodeofbook.book_test.adapters.legacy_weather.LegacyWeatherScreen // Легаси экран
import com.example.kodeofbook.book_test.chapters.auth.AuthChapter // Глава авторизации
import com.example.kodeofbook.book_test.chapters.notes.NotesChapter // Глава заметок
import com.example.kodeofbook.book_test.chapters.profile.ProfileChapter // Глава профиля
import com.example.kodeofbook.book_test.chapters.recovery.RecoveryChapter // Глава восстановления
import com.example.kodeofbook.book_test.introduction.* // Инструменты введения (Bus, Letter, Registry)
import com.example.kodeofbook.book_test.introduction.rules.Rule_Context // Правило контекста
import com.example.kodeofbook.book_test.introduction.rules.Rule_Logger // Логирование
import com.example.kodeofbook.book_test.introduction.screens.auth.AuthScreen // Экран входа
import com.example.kodeofbook.book_test.introduction.screens.notes.NotesScreen // Экран заметок
import com.example.kodeofbook.book_test.introduction.screens.profile.ProfileScreen // Экран профиля
import com.example.kodeofbook.book_test.introduction.screens.recovery.RecoveryScreen // Экран восстановления
import com.example.kodeofbook.ui.theme.KodeofbookTheme // Тема

class MainActivity : ComponentActivity() { // Главный Переплёт Книги (Книга)

    private var currentScreen by mutableStateOf("auth") // Состояние текущей открытой страницы

    override fun onCreate(savedInstanceState: Bundle?) { // Точка входа
        super.onCreate(savedInstanceState) // Вызов родителя
        Rule_Context.app = applicationContext // Инициализация физики контекста

        // 1. Регистрация Книги (навигация)
        Bus.registry["Book"] = object : BaseChapter("Book") { // Сама книга как глава
            override fun invoke(l: Letter) { // Прием писем
                Rule_Logger("Book", "Событие навигации: ${l.action} -> ${l.data}") // Лог перехода
                if (l.action == "OpenScreen") { // Если команда — перевернуть страницу
                    currentScreen = l.data as String // Перелистываем
                } // Конец условия
            } // Конец invoke
        } // Конец регистрации

        // 2. Регистрация Глав (мозгов)
        Bus.chapters["AuthChapter"] = { AuthChapter() } // Глава авторизации
        Bus.chapters["ProfileChapter"] = { ProfileChapter() } // Глава профиля
        Bus.chapters["RecoveryChapter"] = { RecoveryChapter() } // Глава восстановления
        Bus.chapters["NotesChapter"] = { NotesChapter() } // Глава заметок
        Bus.chapters["LegacyWeatherAdapter"] = { LegacyWeatherAdapter() } // Глава адаптера легаси
        
        // Регистрация Глав из других книг с полными путями
        Bus.chapters["BookMath/MathChapter"] = { MathChapter() }
        Bus.chapters["BookNetwork/Chapter"] = { NetworkChapter() }

        // 3. Регистрация страниц в Оглавлении (ScreenRegistry)
        ScreenRegistry.register("auth") { AuthScreen() } // Регистрация экрана входа
        ScreenRegistry.register("notes") { NotesScreen() } // Регистрация экрана заметок
        ScreenRegistry.register("profile") { ProfileScreen() } // Регистрация экрана профиля
        ScreenRegistry.register("recovery") { RecoveryScreen() } // Регистрация экрана восстановления
        
        // Регистрация экранов из других книг
        ScreenRegistry.register("math") { MathScreen() }
        ScreenRegistry.register("network") { NetworkScreen() }

        ScreenRegistry.register("legacy_weather") { // Регистрация легаси-экрана
            LegacyWeatherScreen(onGoToAuth = { 
                Bus.send(Letter("Book", "OpenScreen", "auth", from = "LegacyScreen")) 
            })
        } // Конец регистрации оглавления

        enableEdgeToEdge() // Современный вид интерфейса
        setContent { // Запуск Compose UI
            KodeofbookTheme { // Применяем тему оформления
                Scaffold( // Базовый каркас
                    modifier = Modifier.fillMaxSize(), // Растягиваем на весь экран
                    bottomBar = { // Нижняя панель навигации
                        NavigationBar { // Компонент панели
                            NavigationBarItem(
                                selected = currentScreen == "auth", // Подсветка
                                onClick = { Bus.send(Letter("Book", "OpenScreen", "auth", from = "UI")) }, // Письмо Книге
                                icon = { Icon(Icons.Default.Lock, null) },
                                label = { Text("Вход") }
                            )
                            NavigationBarItem(
                                selected = currentScreen == "math",
                                onClick = { Bus.send(Letter("Book", "OpenScreen", "math", from = "UI")) },
                                icon = { Icon(Icons.Default.Add, null) },
                                label = { Text("Математика") }
                            )
                            NavigationBarItem(
                                selected = currentScreen == "network",
                                onClick = { Bus.send(Letter("Book", "OpenScreen", "network", from = "UI")) },
                                icon = { Icon(Icons.Default.Share, null) },
                                label = { Text("Сеть") }
                            )
                            NavigationBarItem(
                                selected = currentScreen == "profile",
                                onClick = { Bus.send(Letter("Book", "OpenScreen", "profile", from = "UI")) },
                                icon = { Icon(Icons.Default.Person, null) },
                                label = { Text("Профиль") }
                            )
                        } // Конец NavigationBar
                    } // Конец bottomBar
                ) { innerPadding -> // Область контента
                    Surface(modifier = Modifier.padding(innerPadding)) { // Слой подложки с отступами
                        // Использование Реестра: одна строка вместо 25 блоков when
                        ScreenRegistry.Show(currentScreen) // Магия: Книга сама находит и рисует нужную страницу
                    } // Конец Surface
                } // Конец Scaffold
            } // Конец темы
        } // Конец setContent
    } // Конец onCreate
} // Конец MainActivity
