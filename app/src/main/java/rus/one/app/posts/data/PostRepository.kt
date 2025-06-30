package rus.one.app.posts.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import rus.one.app.posts.Post
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
    private val postApiService: PostApiService,
    private val postDao: PostDao
) {

    //  Получаем Flow из DAO
    @RequiresApi(Build.VERSION_CODES.O)
    val posts: Flow<List<Post>> = postDao.getAllPostsFlow()
        .map { entities -> entities.map { it.toDto() } }

    sealed class FetchResult {
        data class Success(val posts: List<Post>) : FetchResult()
        data class Error(val errorMessage: String) : FetchResult()
    }

    suspend fun fetchPosts(): FetchResult {
        return try {
            val response = postApiService.getAllSuspend()
            if (response.isSuccessful) {
                val postsList = response.body() ?: emptyList()
                val entities = postsList.map { PostEntity.fromDto(it) }
                postDao.insert(entities)
                FetchResult.Success(postsList)
            } else {
                val errorBody = response.errorBody()?.string()
                FetchResult.Error("Ошибка API: ${response.code()} $errorBody")
            }
        } catch (e: Exception) {
            FetchResult.Error(e.message ?: "Неизвестная ошибка")
        }
    }


    suspend fun addPost(post: Post) {
        try {
            val response = postApiService.save(post)
            if (response.isSuccessful) {
                response.body()?.let { newPost ->
                    //  TODO:  Обновить локальный пост с новым ID
                }
            } else {
                Log.e("PostRepository", "Ошибка API при добавлении поста: ${response.code()}")
                //  TODO:  Передать ошибку в ViewModel
            }
        } catch (e: Exception) {
            Log.e("PostRepository", "Ошибка при добавлении поста: ${e.message}")
            //  TODO:  Передать ошибку в ViewModel
        }
    }

    suspend fun savePostLocally(post: Post) {
        val entity = PostEntity.Companion.fromDto(post.copy(id = -System.currentTimeMillis())) //  Генерируем отрицательный ID
        postDao.insert(entity)
    }

    suspend fun editPost(post: Post) {
        postDao.updateContentById(post.id, post.content)
    }

    suspend fun likePost(postId: Long): Boolean {
        return try {
            val response = postApiService.likeByID(postId)
            if (response.isSuccessful) {
                response.body()?.let { updatedPost ->
                    postDao.insert(PostEntity.fromDto(updatedPost)) // Вызов insert из DAO
                }
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }


    suspend fun dislikePost(postId: Long): Boolean {
        return try {
            val response = postApiService.dislikeByID(postId)
            if (response.isSuccessful) {
                response.body()?.let { updatedPost ->
                    postDao.insert(PostEntity.fromDto(updatedPost))
                }
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deletePost(post: Post) {
        postDao.removeById(post.id)
        postApiService.removeByID(post.id)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun syncUnsyncedPosts() {
        val unsyncedPosts = postDao.getUnsyncedPosts()
        for (entity in unsyncedPosts) {
            try {
                val post = entity.toDto()
                val response = postApiService.save(post)
                if (response.isSuccessful) {
                    response.body()?.let { newPost ->
                        postDao.insert(PostEntity.Companion.fromDto(newPost).copy(id = entity.id)) //  Обновляем ID
                    }
                } else {
                    Log.e("PostRepository", "Ошибка API при синхронизации поста: ${response.code()}")
                    //  TODO:  Обработать ошибку
                }
            } catch (e: Exception) {
                Log.e("PostRepository", "Ошибка при синхронизации поста: ${e.message}")
                //  TODO:  Обработать ошибку
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getPostById(postId: Long): Post? {
        val entity = postDao.getPostById(postId)
        return entity?.toDto()
    }
}