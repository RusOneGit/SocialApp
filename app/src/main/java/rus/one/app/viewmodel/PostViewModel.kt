package rus.one.app.viewmodel


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rus.one.app.common.FetchResult
import rus.one.app.posts.Post
import rus.one.app.posts.data.PostRepository
import rus.one.app.profile.UserRepository
import rus.one.app.state.FeedState
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class PostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
) : BaseFeedViewModel<Post>() {

    private val _isLiked = MutableStateFlow<Map<Long, Boolean>>(emptyMap())
    val isLiked: StateFlow<Map<Long, Boolean>> = _isLiked

    private val _uiMessage = MutableSharedFlow<String>()
    val uiMessage = _uiMessage.asSharedFlow()

    override val feedState = MutableStateFlow(FeedState<Post>(loading = true))
    override fun load() {
        if (feedState.value.isRefreshing) return
        feedState.update {
            it.copy(
                isRefreshing = true,
                loading = true,
                error = false
            )
        } // Добавим loading = true
        viewModelScope.launch {


            try {
                val result =
                    postRepository.fetchPosts() // Предположим, result содержит данные или ошибку

                if (result is FetchResult.Success) {
                    // Если fetchPosts сам возвращает данные для отображения:
                    feedState.update {
                        it.copy(
                            item = result.item as List<Post>, // Обновляем item здесь
                            empty = result.item.isEmpty(),
                            error = false
                        )
                    }
                } else if (result is FetchResult.Error) {
                    Log.e("ViewModelCard", "Ошибка загрузки постов: ${result.errorMessage}")
                    feedState.update {
                        it.copy(
                            error = true,
                            loading = false
                        )
                    } // Оставляем текущий item или делаем его пустым
                }
            } catch (e: Exception) {
                Log.e("ViewModelCard", "Исключение при загрузке: ${e.message}")
                feedState.update { it.copy(error = true) }
            } finally {
                feedState.update {

                    it.copy(
                        isRefreshing = false,
                        loading = false
                    )
                } // Сбрасываем оба флага
            }
        }
    }

    init {
        observePosts()
        getPosts()
    }

    val _feedState: StateFlow<FeedState<Post>> = feedState.asStateFlow()


    private val _likesCount = MutableStateFlow<Map<Long, Int>>(emptyMap())
    val likesCount: StateFlow<Map<Long, Int>> = _likesCount


    override fun like(postID: Long) {
        viewModelScope.launch {
            val token = userRepository.tokenFlow.first()
            if (token.isBlank()) {
                _uiMessage.emit("Ты не авторизован")
            } else {
                postRepository.likePost(postID) // предполагается, что такой метод есть
                _uiMessage.emit("Лайк поставлен")
            }
        }

    }

    override fun add(item: Post) {
        viewModelScope.launch { postRepository.addPost(item as Post) }

    }

    override fun edit(item: Post) {
        viewModelScope.launch {
            postRepository.editPost(item as Post)
        }
    }

    override fun refresh() {


    }

    private fun observePosts() {
        viewModelScope.launch {
            postRepository.posts.collect { postsList ->
                feedState.update { currentState ->
                    currentState.copy(
                        item = postsList,
                        empty = postsList.isEmpty()

                    )
                }
            }
        }
    }

    fun getPosts(){
        viewModelScope.launch {
            postRepository.fetchPosts()
        }
    }

    fun dislikePost(postId: Long) {
        viewModelScope.launch {
            postRepository.dislikePost(postId)

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


        fun observePosts() {
            viewModelScope.launch {
                postRepository.posts.collect { postsList ->
                    feedState.update { currentState ->
                        currentState.copy(
                            item = postsList,
                            empty = postsList.isEmpty()

                        )
                    }
                }
            }
        }





        fun add(post: Post) {

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

        }

        }
    }