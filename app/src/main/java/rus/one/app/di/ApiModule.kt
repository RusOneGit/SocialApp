package rus.one.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rus.one.app.BuildConfig
import rus.one.app.api.ApiKeyInterceptor
import rus.one.app.api.AuthorizationInterceptor
import rus.one.app.api.TokenProvider
import rus.one.app.events.data.EventApiService
import rus.one.app.posts.data.PostApiService
import rus.one.app.profile.UserApiService
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "${BuildConfig.BASE_URL}api/"

    @Provides
    @Singleton
    @Named("apiKey")
    fun provideApiKey(): String = BuildConfig.API_KEY

    // Удаляем provideAuthKey — токен теперь динамический

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named("apiKey") apiKey: String,
        tokenProvider: TokenProvider, // Инжектируем провайдера токена
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(apiKey))
            .addInterceptor(AuthorizationInterceptor(tokenProvider)) // передаем провайдера
            .connectTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providePostApiService(retrofit: Retrofit): PostApiService =
        retrofit.create(PostApiService::class.java)

    @Provides
    @Singleton
    fun provideEventApiService(retrofit: Retrofit): EventApiService =
        retrofit.create(EventApiService::class.java)

    @Provides
    @Singleton
    fun provideUserApiService(retrofit: Retrofit): UserApiService =
        retrofit.create(UserApiService::class.java)
}
