package rus.one.app.viewmodel


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import rus.one.app.posts.Post
import rus.one.app.posts.data.PostRepository
import javax.inject.Inject
import kotlin.math.max

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ViewModelCard @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val _isLiked = MutableStateFlow<Map<Long, Boolean>>(emptyMap())
    val isLiked: StateFlow<Map<Long, Boolean>> = _isLiked

    private val _likesCount = MutableStateFlow<Map<Long, Int>>(emptyMap())
    val likesCount: StateFlow<Map<Long, Int>> = _likesCount

    fun toggleLike(postID: Long) {
        val currentLiked = _isLiked.value[postID]
            ?: false // Получаем текущее состояние лайка для конкретного поста
        val currentLikes = _likesCount.value[postID]
            ?: 0 // Получаем текущее количество лайков для конкретного поста

        // Обновляем состояние лайка
        _isLiked.value = _isLiked.value.toMutableMap().apply {
            this[postID] = !currentLiked
        }

        // Обновляем количество лайков
        _likesCount.value = _likesCount.value.toMutableMap().apply {
            this[postID] = if (currentLiked) max(currentLikes - 1, 0) else currentLikes + 1
        }
    }

    val posts: StateFlow<List<Post>> = repository.posts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val events: StateFlow<List<Post>> = repository.posts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        getPosts()
    }

    fun add(post: Post) {
        viewModelScope.launch {
            repository.savePostLocally(post) //  Сохраняем локально
        }
    }

    fun edit(post: Post) {
        viewModelScope.launch {
            repository.editPost(post)
        }
    }

    fun delete(post: Post) {
        viewModelScope.launch {
            repository.deletePost(post)
        }
    }

    fun getPosts() {
        viewModelScope.launch {
            repository.fetchPosts()
        }
    }

    fun syncUnsyncedPosts() {
        viewModelScope.launch {
            repository.syncUnsyncedPosts()
        }
    }
}