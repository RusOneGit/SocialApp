package rus.one.app.viewmodel


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import rus.one.app.events.Event
import rus.one.app.events.data.EventRepository
import rus.one.app.posts.Post
import rus.one.app.posts.data.PostRepository
import rus.one.app.profile.User
import rus.one.app.profile.UserRepository
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ViewModelCard @Inject constructor(
    private val postRepository: PostRepository,
    private val eventRepository: EventRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _isLiked = MutableStateFlow<Map<Long, Boolean>>(emptyMap())
    val isLiked: StateFlow<Map<Long, Boolean>> = _isLiked

    private val _likesCount = MutableStateFlow<Map<Long, Int>>(emptyMap())
    val likesCount: StateFlow<Map<Long, Int>> = _likesCount

    fun likePost(postId: Long) {
        viewModelScope.launch {
            val success = postRepository.likePost(postId)
            if (!success) {
                // Обработка ошибки, например, показать сообщение
            }
        }
    }

    fun dislikePost(postId: Long) {
        viewModelScope.launch {
            val success = postRepository.dislikePost(postId)
            if (!success) {
                // Обработка ошибки
            }
        }
    }

    val posts: StateFlow<List<Post>> = postRepository.posts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    ).also {
        viewModelScope.launch {
            it.collect { list ->
                Log.d("ViewModelCard", "Посты обновились: ${list.size} штук")
            }
        }
    }

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

    val users: StateFlow<List<User>> = userRepository.users.stateIn(
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
        getPosts()
        getEvents()
        getUsers()
    }


    fun add(post: Post) {
        viewModelScope.launch {
            postRepository.savePostLocally(post) //  Сохраняем локально
        }
    }

    fun edit(post: Post) {
        viewModelScope.launch {
            postRepository.editPost(post)
        }
    }

    fun delete(post: Post) {
        viewModelScope.launch {
            postRepository.deletePost(post)
        }
    }

    fun getPosts() {
        viewModelScope.launch {
            postRepository.fetchPosts()
        }
    }

    fun syncUnsyncedPosts() {
        viewModelScope.launch {
            postRepository.syncUnsyncedPosts()
        }
    }

    fun getEvents() {
        viewModelScope.launch {
            eventRepository.fetchEvents()

        }
    }

    fun getUsers() {
        viewModelScope.launch {
            userRepository.fetchUsers()
        }


    }
}