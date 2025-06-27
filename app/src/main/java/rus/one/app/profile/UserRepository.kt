package rus.one.app.profile

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import rus.one.app.util.dataStore
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApiService: UserApiService,
    private val userDao: UserDao,
    @ApplicationContext context: Context,
    private val userPreferences: UserPreferences,
) {

    private val dataStore = context.dataStore

    companion object {
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val USER_ID_KEY = longPreferencesKey("user_id")
    }

    val tokenFlow: Flow<String> = dataStore.data
        .map { prefs -> prefs[TOKEN_KEY] ?: "" }

    val userIdFlow: Flow<Long> = dataStore.data
        .map { prefs -> prefs[USER_ID_KEY] ?: 0L }

    @RequiresApi(Build.VERSION_CODES.O)
    val users: Flow<List<User>> = userDao.getAll()
        .map { list -> list.map { it.toDto() } }
        .flowOn(Dispatchers.IO)

    suspend fun fetchUsers() {
        try {
            val response = userApiService.getUsers()
            if (response.isSuccessful) {
                response.body()?.let { userList ->
                    val entities = userList.map { UserEntity.fromDto(it) }
                    userDao.insert(entities)
                }
            } else {
                Log.e(
                    "UserRepository",
                    "API error: ${response.code()}, body: ${response.errorBody()?.string()}"
                )
            }
        } catch (e: Exception) {
            Log.e("UserRepository", "Error fetching users: ${e.message}")
        }
    }

    suspend fun getUserById(id: Long) = userApiService.getUserById(id)

    suspend fun registration(
        login: String,
        password: String,
        name: String,
        avatar: Uri,
        contentResolver: ContentResolver,
    ) {
        val inputStream = contentResolver.openInputStream(avatar)
            ?: throw IllegalArgumentException("Cannot open avatar Uri")
        val tempFile = File.createTempFile("avatar", ".jpg")
        tempFile.outputStream().use { output -> inputStream.copyTo(output) }

        val mimeType = contentResolver.getType(avatar) ?: "image/jpeg"
        val requestBody = tempFile.asRequestBody(mimeType.toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData("file", tempFile.name, requestBody)

        userApiService.registerUser(
            login = login,
            pass = password,
            name = name,
            file = multipartBody
        )

        tempFile.delete()
    }

    suspend fun authentication(login: String, password: String): AuthResponse {
        val authResponse = userApiService.authenticateUser(login = login, pass = password)

        dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = authResponse.token
            prefs[USER_ID_KEY] = authResponse.userId
        }

        return authResponse
    }

    suspend fun clearAuthData() {
        dataStore.edit { prefs ->
            prefs.remove(TOKEN_KEY)
            prefs.remove(USER_ID_KEY)
        }
    }
}
