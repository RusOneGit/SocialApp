package rus.one.app.profile

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApiService {
    @Multipart
    @POST("/api/users/registration")
    suspend fun registerUser(
        @Query("login") login: String,
        @Query("pass") pass: String,
        @Query("name") name: String,
        @Part file: MultipartBody.Part
    ): AuthResponse

    @POST("/api/users/authentication")
    suspend fun authenticateUser(
        @Query("login") login: String,
        @Query("pass") pass: String,
    ): AuthResponse

    @GET("/api/{id}/jobs")
    suspend fun getJobs(@Path("id") id: Long): Response<List<Jobs>>

    @GET("/api/users")
    suspend fun getUsers(): Response<List<User>>

    @GET("/api/users/{id}")
    suspend fun getUserById(@Path("id") id: Long): User
}