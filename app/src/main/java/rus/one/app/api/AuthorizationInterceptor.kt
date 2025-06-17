package rus.one.app.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val authToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", authToken)
            .build()

        Log.d("Auth", "Authorization header added: ${newRequest.header("Authorization")}")
        Log.d("Auth", "Full request: ${newRequest.toString()}")

        return chain.proceed(newRequest)
    }
}
