package junction.brunz.data.network

import io.reactivex.schedulers.Schedulers
import junction.brunz.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Long Vu on 7.11.2020
 */
object RetrofitProvider {

  fun provide(): Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.AITO_INSTANCE_URL)
    .client(
      OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()
    )
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
    .build()
}