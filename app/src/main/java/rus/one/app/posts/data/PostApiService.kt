package rus.one.app.posts.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import rus.one.app.posts.Post

interface PostApiService {

    @GET("item")
    suspend fun getAllSuspend(): Response<List<Post>>

    @POST("item")
    suspend fun save(@Body post: Post): Response<Post>

    @POST("item/{id}/likes")
    suspend fun likeByID(@Path("id") id: Long): Response<Post>

    @DELETE("item/{id}/likes")
    suspend fun dislikeByID(@Path("id") id: Long): Response<Post>

    @GET("item/{id}/newer")
    suspend fun getNewer(@Path("id") id: Long): Response<List<Post>>

    @GET("item/{id}/before")
    suspend fun getBefore(@Path("id") id: Long): Response<List<Post>>

    @GET("item/{id}/after")
    suspend fun getAfter(@Path("id") id: Long): Response<List<Post>>

    @GET("item/{id}")
    suspend fun getByID(@Path("id") id: Long): Response<Post>

    @DELETE("item/{id}")
    suspend fun removeByID(@Path("id") id: Long): Response<Unit>

    @GET("item/latest")
    suspend fun getLatest(): Response<List<Post>>
}
