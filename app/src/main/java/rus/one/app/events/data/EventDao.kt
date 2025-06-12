package rus.one.app.events.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import rus.one.app.events.EventEntity

@Dao
interface EventDao {
    @Query("SELECT * FROM EventEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<EventEntity>>

    @Query("SELECT * FROM EventEntity ORDER BY id DESC")
    fun getAllEventsFlow(): Flow<List<EventEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: EventEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(events: List<EventEntity>)

    @Query("SELECT * FROM EventEntity WHERE id = :eventId LIMIT 1")
    suspend fun getEventById(eventId: Long): EventEntity?

    @Update
    suspend fun update(events: EventEntity)

    @Query("UPDATE EventEntity SET content = :content WHERE id = :eventId")
    suspend fun updateContentById(eventId: Long, content: String)


    @Query("DELETE FROM EventEntity WHERE id = :id")
    suspend fun removeById(id: Long)

    @Query("SELECT * FROM EventEntity WHERE id < 0")
    suspend fun getUnsyncedEvents(): List<EventEntity>

    @Query("SELECT COUNT(*) FROM EventEntity")
    suspend fun getCount(): Int
}
