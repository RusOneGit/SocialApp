package rus.one.app.posts.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import rus.one.app.posts.Post

interface PostApiService {

    @GET("posts")
    suspend fun getAllSuspend(): Response<List<Post>>

    @GET("posts/{id}")
    suspend fun getByID(@Path("id") id: Long): Response<Post>

    @POST("posts")
    suspend fun save(@Body post: Post): Response<Post>

    @DELETE("posts/{id}")
    suspend fun removeByID(@Path("id") id: Long): Response<Unit>

    @POST("posts/{id}/likes")
    suspend fun likeByID(@Path("id") id: Long): Response<Post>

    @DELETE("posts/{id}/likes")
    suspend fun dislikeByID(@Path("id") id: Long): Response<Post>
}