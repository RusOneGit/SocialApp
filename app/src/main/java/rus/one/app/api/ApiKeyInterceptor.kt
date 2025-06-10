package rus.one.app.api


import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", apiKey) // или другой заголовок
            .build()

        Log.d("ApiKeyInterceptor", "Authorization header added: ${newRequest.header("Authorization")}")

        return chain.proceed(newRequest)
    }
}
