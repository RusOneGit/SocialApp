package rus.one.app.api


import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Api-Key", apiKey) // Используем заголовок Api-Key
            .build()

        Log.d("ApiKeyInterceptor", "Api-Key header added: ${newRequest.header("Api-Key")}")
        Log.d("ApiKeyInterceptor", "Full request: ${newRequest}")

        return chain.proceed(newRequest)
    }
}
