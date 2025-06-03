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
import retrofit2.Call
import rus.one.app.api.PostApiService
import rus.one.app.posts.Post
import rus.one.app.posts.post
import rus.one.app.profile.user
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Singleton
class PostRepository @Inject constructor(  private val postApiService: PostApiService) {

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts.asStateFlow()

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        // Вызов API для получения постов
        postApiService.getAll().enqueue(object : retrofit2.Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: retrofit2.Response<List<Post>>) {
                if (response.isSuccessful) {
                    response.body()?.let { postsList ->
                        _posts.value = postsList

                    }

                    Log.e("PostRepository", "а может")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                // Обработка ошибок
                Log.e("PostRepository", "Ошибка при получении постов: ${t.message}")
            }
        })
    }


    fun addPost(post: Post) {
        postApiService.save(post).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(call: Call<Post>, response: retrofit2.Response<Post>) {
                if (response.isSuccessful) {
                    response.body()?.let { newPost ->
                        _posts.value = _posts.value + newPost
                    }
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e("PostRepository", "Ошибка при добавлении поста: ${t.message}")
            }
        })
    }

    fun editPost(post: Post) {
        _posts.value = _posts.value.map {
            if (it.id == post.id) post else it
        }
    }

    fun getPosts(): Flow<List<Post>> {
        return posts
    }

    fun deletePost(post: Post) {
        _posts.value = _posts.value.filter { it.id != post.id }
    }
}
