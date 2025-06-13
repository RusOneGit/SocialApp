package rus.one.app.profile

import com.google.android.gms.identitycredentials.RegistrationRequest
import com.google.android.gms.identitycredentials.RegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {

    @POST("/api/users/registration")
    suspend fun registerUser(@Body request: RegistrationRequest): RegistrationResponse

//    @POST("/api/users/authentication")
//    suspend fun authenticateUser(@Body request: AuthenticationRequest): AuthenticationResponse

    @GET("/api/users")
    suspend fun getUsers(): Response<List<User>>

    @GET("/api/users/{id}")
    suspend fun getUserById(@Path("id") id: Long): User
}