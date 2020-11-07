package junction.brunz.data.network

import io.reactivex.Single
import junction.brunz.data.model.base.RecommendRequest
import junction.brunz.data.model.base.SearchRequest
import junction.brunz.data.model.place.PlacesResponse
import junction.brunz.data.model.user.UserModel
import junction.brunz.data.model.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Long Vu on 6.11.2020
 */
interface AitoApi {

  @POST("api/v1/_query")
  fun getRecommendPlaces(@Body request: RecommendRequest): Single<PlacesResponse>

  @POST("api/v1/_search")
  fun getUser(@Body request: SearchRequest): Single<UserResponse>

  @POST("api/v1/data/users")
  fun createUser(@Body model: UserModel): Single<UserModel>

  companion object {

    private val instance = RetrofitProvider.provide()
      .create(AitoApi::class.java)

    fun get(): AitoApi = instance
  }
}