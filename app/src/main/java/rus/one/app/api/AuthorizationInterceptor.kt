package rus.one.app.api

import android.util.Log
import kotlinx.coroutines.flow.StateFlow
import okhttp3.Interceptor
import okhttp3.Response


class AuthorizationInterceptor(
    private val tokenProvider: TokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // Берём текущий токен из провайдера (синхронно)
        val token = tokenProvider.authToken.value

        val requestBuilder = chain.request().newBuilder()

        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", token)
        }

        val newRequest = requestBuilder.build()

        Log.d("AuthorizationInterceptor", "Authorization header added: ${newRequest.header("Authorization")}")
        Log.d("AuthorizationInterceptor", "Request URL: ${newRequest.url}")

        return chain.proceed(newRequest)
    }
}


interface TokenProvider {
    val authToken: StateFlow<String?>
}
