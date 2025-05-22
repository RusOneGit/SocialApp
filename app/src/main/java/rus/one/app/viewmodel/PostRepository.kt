package rus.one.app.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import rus.one.app.posts.Post
import rus.one.app.posts.post
import rus.one.app.profile.user
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Singleton
class PostRepository @Inject constructor() {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    init {
        // Инициализация начальными данными
        val initialPosts = listOf(
            Post(
                id = 0,
                author = user,
                content = "похуй 300",
                date = LocalDateTime.now()
            )
        )
        _posts.value = initialPosts
    }

    fun addPost(post: Post) {

        _posts.value = _posts.value + post

        Log.d("размер", "$_posts.value.size")
    }

    fun editPost(post: Post) {
        _posts.value = _posts.value.map {
            if (it.id == post.id) post else it
        }
    }

    fun getPosts(): Flow<List<Post>> {
        return posts
    }
}
