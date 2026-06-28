package com.example.kodeofbook.book_test.introduction.rules

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Правило: Работа с базой данных заметок.
 * Это «физика» вашего мира: как хранятся данные.
 * Использует глобальный Rule_Context.
 */

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY timestamp DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)
}

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}

object Rule_NotesDb {
    private var db: NotesDatabase? = null

    private fun getDb(): NotesDatabase {
        if (db == null) {
            val context = Rule_Context.app ?: throw Exception("Ошибка физики: Rule_Context.app не инициализирован в MainActivity!")
            db = Room.databaseBuilder(
                context,
                NotesDatabase::class.java, "notes_db"
            ).build()
        }
        return db!!
    }

    fun dao(): NoteDao = getDb().noteDao()
}
