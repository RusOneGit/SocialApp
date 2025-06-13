package rus.one.app.profile

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApiService: UserApiService,
    private val userDao: UserDao,

    ) {
    //  Получаем Flow из DAO
    @RequiresApi(Build.VERSION_CODES.O)
    val users: Flow<List<User>> = userDao.getAll().map { list ->
        list.map { it.toDto() }
    }.flowOn(Dispatchers.IO) //  Выполняем преобразование в IO потоке


    suspend fun fetchUsers() {
        try {
            val response = userApiService.getUsers()
            if (response.isSuccessful) {
                response.body()?.let { userList ->

                    val entities = userList.map { UserEntity.Companion.fromDto(it) }
                    userDao.insert(entities) //  Используем insert(List<PostEntity>)
                }

            } else {
                Log.e(
                    "PostRepository",
                    "Ошибка API: ${response.code()}, тело: ${response.errorBody()?.string()}"
                )
            }
        } catch (e: Exception) {
            Log.e("PostRepository", "Ошибка при получении постов: ${e.message}")
            //  TODO:  Передать ошибку в ViewModel
        }
    }
}