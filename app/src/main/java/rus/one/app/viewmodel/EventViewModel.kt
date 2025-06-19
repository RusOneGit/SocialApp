package rus.one.app.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rus.one.app.common.FetchResult
import rus.one.app.common.Item
import rus.one.app.events.Event
import rus.one.app.events.data.EventRepository
import rus.one.app.posts.Post
import rus.one.app.state.FeedState
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository,

) : BaseFeedViewModel<Event>() {

    override val feedState = MutableStateFlow(FeedState<Event>(loading = true))
    val _feedState: StateFlow<FeedState<Event>> = feedState.asStateFlow()



//    override val feedState = MutableStateFlow(FeedState<Event>(loading = true))
//    val _feedState: StateFlow<FeedState<Event>> = feedState.asStateFlow()



    // Публичное свойство, реализующее абстрактное feedState


    val events: StateFlow<List<Event>> = eventRepository.events.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    ).also {
        viewModelScope.launch {
            it.collect { list ->
                Log.d("ViewModelCard", "События  обновились: ${list} штук")
            }
        }
    }

    init {
        observeEvents()
        getEvents()
    }

    fun getEvents() {
        viewModelScope.launch {
            eventRepository.fetchEvents()

        }
    }




    private fun observeEvents() {
        viewModelScope.launch {
            eventRepository.events.collect { eventsList ->
                feedState.update { currentState ->
                    currentState.copy(
                        item = eventsList,
                        empty = eventsList.isEmpty()

                    )
                }
            }
        }
    }

    override fun like(itemID: Long) {

    }

    override fun add(item: Item) {
        TODO("Not yet implemented")
    }

    override fun edit(item: Item) {
        TODO("Not yet implemented")
    }

    override fun load() {
        if (feedState.value.isRefreshing) return
        feedState.update {
            it.copy(
                isRefreshing = true, loading = true, error = false
            )
        } // Добавим loading = true
        viewModelScope.launch {


            try {
                val result =
                    eventRepository.fetchEvents() // Предположим, result содержит данные или ошибку

                if (result is FetchResult.Success) {
                    // Если fetchPosts сам возвращает данные для отображения:
                    feedState.update {
                        it.copy(
                            item = result.item as List<Event>,// Обновляем item здесь
                            empty = result.item.isEmpty(),
                            error = false
                        )
                    }
                } else if (result is FetchResult.Error) {
                    Log.e("ViewModelCard", "Ошибка загрузки постов: ${result.errorMessage}")
                    feedState.update {
                        it.copy(
                            error = true, loading = false
                        )
                    } // Оставляем текущий item или делаем его пустым
                }
            } catch (e: Exception) {
                Log.e("ViewModelCard", "Исключение при загрузке: ${e.message}")
                feedState.update { it.copy(error = true) }
            } finally {
                feedState.update {

                    it.copy(
                        isRefreshing = false, loading = false
                    )
                } // Сбрасываем оба флага
            }
        }
    }

    override fun refresh() {
        TODO("Not yet implemented")
    }


}