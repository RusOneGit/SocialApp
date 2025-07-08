package rus.one.app.profile

import com.google.gson.annotations.SerializedName


data class User(
    val id: Long,
    val avatar: String?,
    val name: String,
    val login: String,
    val token: String?,
)


data class RegistrationRequest(

    @SerializedName("login") val login: String,
    @SerializedName("pass") val password: String,
    @SerializedName("name") val name: String,

    )

data class AuthenticationRequest(

    @SerializedName("login") val login: String,
    @SerializedName("pass") val password: String

    )

data class AuthResponse(
    @SerializedName("id")
    val userId: Long,
    val token: String,
    val avatar: String?,
)














//    val job: MutableList<Jobs>? = null,
//    val friends: MutableList<User>? = null,
//
//    ){
//    val actualJob: String?
//        get() = job?.firstOrNull()?.title
//}
