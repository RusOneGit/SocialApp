package rus.one.app.events.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import rus.one.app.events.Event
import rus.one.app.events.EventEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    private val eventApiService: EventApiService,
    private val eventDao: EventDao,
) {

    @RequiresApi(Build.VERSION_CODES.O)
    val events: Flow<List<Event>> = eventDao.getAllEventsFlow().map { list ->
        list.map { it.toDto() }
    }.flowOn(Dispatchers.IO) // Выполняем преобразование в IO потоке

    suspend fun fetchEvents() {
        try {
            val response = eventApiService.getAllSuspend()
            if (response.isSuccessful) {
                response.body()?.let { eventsList ->

                    Log.d("Количество", "Успешный ответ, тело: ${eventsList.size}")
                    val entities = eventsList.map { EventEntity.fromDto(it) }
                    eventDao.insert(entities)
                    try {
                        val count = eventDao.getCount()
                        Log.d("Количество", "Вставлено событий в БД, всего записей: $count")
                    } catch (e: Exception) {
                        Log.e("Количество", "Ошибка при getCount(): ${e.message}", e)
                    }

                // Используем insert(List<EventEntity>)
                }

            } else {
                Log.e(
                    "EventRepository",
                    "Ошибка API: ${response.code()}, тело: ${response.errorBody()?.string()}"
                )
            }
        } catch (e: Exception) {
            Log.e("EventRepository", "Ошибка при получении событий: ${e.message}")
            // TODO: Передать ошибку в ViewModel
        }
    }

    suspend fun addEvent(event: Event) {
        try {
            val response = eventApiService.save(event)
            if (response.isSuccessful) {
                response.body()?.let { newEvent ->
                    // TODO: Обновить локальное событие с новым ID
                }
            } else {
                Log.e("EventRepository", "Ошибка API при добавлении события: ${response.code()}")
                // TODO: Передать ошибку в ViewModel
            }
        } catch (e: Exception) {
            Log.e("EventRepository", "Ошибка при добавлении события: ${e.message}")
            // TODO: Передать ошибку в ViewModel
        }
    }

    suspend fun saveEventLocally(event: Event) {
        val entity =
            EventEntity.fromDto(event.copy(id = -System.currentTimeMillis())) // Генерируем отрицательный ID
        eventDao.insert(entity)
    }

    suspend fun editEvent(event: Event) {
        eventDao.updateContentById(event.id, event.content)
    }

    suspend fun deleteEvent(event: Event) {
        eventDao.removeById(event.id)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun syncUnsyncedEvents() {
        val unsyncedEvents = eventDao.getUnsyncedEvents()
        for (entity in unsyncedEvents) {
            try {
                val event = entity.toDto()
                val response = eventApiService.save(event)
                if (response.isSuccessful) {
                    response.body()?.let { newEvent ->
                        eventDao.insert(
                            EventEntity.fromDto(newEvent).copy(id = entity.id)
                        ) // Обновляем ID
                    }
                } else {
                    Log.e(
                        "EventRepository",
                        "Ошибка API при синхронизации события: ${response.code()}"
                    )
                    // TODO: Обработать ошибку
                }
            } catch (e: Exception) {
                Log.e("EventRepository", "Ошибка при синхронизации события: ${e.message}")
                // TODO: Обработать ошибку
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getEventById(eventId: Long): Event? {
        val entity = eventDao.getEventById(eventId)
        return entity?.toDto()
    }
}
