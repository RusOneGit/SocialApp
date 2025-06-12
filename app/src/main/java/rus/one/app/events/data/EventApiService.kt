package rus.one.app.events.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import rus.one.app.events.Event

interface EventApiService {

    @GET("events")
    suspend fun getAllSuspend(): Response<List<Event>>

    @POST("events")
    suspend fun save(@Body event: Event): Response<Event>

    @POST("events/{id}/participants")
    suspend fun participate(@Path("id") id: Long): Response<Event>

    @DELETE("events/{id}/participants")
    suspend fun leave(@Path("id") id: Long): Response<Event>

    @POST("events/{id}/likes")
    suspend fun likeByID(@Path("id") id: Long): Response<Event>

    @DELETE("events/{id}/likes")
    suspend fun dislikeByID(@Path("id") id: Long): Response<Event>

    @GET("events/{id}/newer")
    suspend fun getNewer(@Path("id") id: Long): Response<List<Event>>

    @GET("events/{id}/before")
    suspend fun getBefore(@Path("id") id: Long): Response<List<Event>>

    @GET("events/{id}/after")
    suspend fun getAfter(@Path("id") id: Long): Response<List<Event>>

    @GET("events/{id}")
    suspend fun getByID(@Path("id") id: Long): Response<Event>

    @DELETE("events/{id}")
    suspend fun removeByID(@Path("id") id: Long): Response<Unit>

    @GET("events/latest")
    suspend fun getLatest(): Response<List<Event>>
}
