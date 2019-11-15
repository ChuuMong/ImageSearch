package space.chuumong.data.remote.utils

import okhttp3.Interceptor
import okhttp3.Response
import space.chuumong.data.BuildConfig
import space.chuumong.data.const.AUTHORIZATION_HEADER
import space.chuumong.data.const.AUTHORIZATION_HEADER_BEARER

class KakaoHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader(
            AUTHORIZATION_HEADER, "$AUTHORIZATION_HEADER_BEARER ${BuildConfig.API_KEY}"
        ).build()

        return chain.proceed(request)
    }
}