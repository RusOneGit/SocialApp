package rus.one.app.api


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import rus.one.app.BuildConfig
import rus.one.app.posts.Post
import java.io.FileInputStream
import java.util.Properties
import java.util.concurrent.TimeUnit

private const val BASE_URL = "${BuildConfig.BASE_URL}api/slow/"
private val apiKey = loadApiKey()

private val client = OkHttpClient.Builder()
    .addInterceptor(ApiKeyInterceptor(apiKey))
    .connectTimeout(30, TimeUnit.SECONDS)
    .let {
        if (BuildConfig.DEBUG) {
            it.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        } else {
            it
        }
    }
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface PostApiService {
    @GET("posts")
    fun getAll(): Call<List<Post>>

    @GET("posts/{id}")
    fun getByID(@Path("id") id: Long): Call<Post>

    @POST("posts")
    fun save(@Body post: Post): Call<Post>

    @DELETE("posts/{id}")
    fun removeByID(@Path("id") id: Long): Call<Unit>

    @POST("posts/{id}/likes")
    fun likeByID(@Path("id") id: Long): Call<Post>

    @DELETE("posts/{id}/likes")
    fun dislikeByID(@Path("id") id: Long): Call<Post>
}

class ApiKeyInterceptor(private val apiKey: String) : okhttp3.Interceptor {
    override fun intercept(chain: okhttp3.Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $apiKey") // или другой заголовок, в зависимости от API
            .build()
        return chain.proceed(newRequest)
    }
}


fun loadApiKey(): String {
    val properties = Properties()
    val inputStream = FileInputStream("api_key.properties")
    properties.load(inputStream)
    return properties.getProperty("API_KEY") ?: throw IllegalArgumentException("API_KEY not found")
}