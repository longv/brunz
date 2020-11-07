package junction.brunz.data.network

import junction.brunz.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Long Vu on 6.11.2020
 */
class AuthInterceptor : Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()
      .newBuilder()
      .addHeader(HEADER_KEY_API, BuildConfig.AITO_API_KEY)
      .build()

    return chain.proceed(request)
  }

  companion object {

    const val HEADER_KEY_API = "X-API-Key"
  }
}