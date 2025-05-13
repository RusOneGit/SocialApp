package rus.one.app.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import rus.one.app.common.Item
import rus.one.app.events.Event
import rus.one.app.events.EventType
import rus.one.app.events.event
import rus.one.app.posts.Post
import rus.one.app.posts.post
import rus.one.app.profile.user
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.math.max

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ViewModelCard @Inject constructor(): ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts
    val edited: MutableLiveData<Post> = MutableLiveData()

    init {
        // Заполнение списка постов
        val postList = mutableListOf<Post>().apply {
            repeat(1) {
                add(
                    Post(
                        id = it,
                        author = user,
                        content = post.content,
                        date = LocalDateTime.now()
                    )
                )
            }
        }
        _posts.value = postList
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events

    init {
        // Заполнение списка постов
        val eventsList = mutableListOf<Event>().apply {
            repeat(10) {
                add(
                    Event(
                        id = it,
                        author = user,
                        content = event.content,
                        eventType = EventType.Online,
                        eventDate = LocalDateTime.now(),
                        date = LocalDateTime.now()
                    )
                )
            }
        }
        _events.value = eventsList
    }


    private val _itemCard = MutableLiveData<Item>()
    val itemCard: LiveData<Item> = _itemCard

    private val _isLiked = MutableStateFlow(false)
    val isLiked: StateFlow<Boolean> = _isLiked

    private val _likesCount = MutableStateFlow(0)
    val likesCount: StateFlow<Int> = _likesCount

    fun toggleLike() {
        val currentLiked = _isLiked.value
        _isLiked.value = !currentLiked
        _likesCount.value =
            if (currentLiked) max(_likesCount.value - 1, 0) else _likesCount.value + 1
    }

    fun add(poster: Post) {
        // Получаем текущее состояние списка постов
        val currentPosts = _posts.value.toMutableList()

        // Добавляем новый пост в список
        currentPosts.add(poster)

        // Обновляем состояние _posts с новым списком
        _posts.value = currentPosts
    }

    fun edit(poster: Post) {


    }
}