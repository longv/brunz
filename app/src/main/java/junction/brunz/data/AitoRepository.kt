package junction.brunz.data

import android.util.Log
import io.reactivex.Single
import junction.brunz.data.model.base.OperatorRequest
import junction.brunz.data.model.base.PrepositionRequest
import junction.brunz.data.model.base.RecommendRequest
import junction.brunz.data.model.base.SearchRequest
import junction.brunz.data.model.place.PlaceModel
import junction.brunz.data.model.user.UserModel
import junction.brunz.data.network.AitoApi
import junction.brunz.presentation.profile.ProfileCreateFragment
import java.util.*

/**
 * Created by Long Vu on 7.11.2020
 */
object AitoRepository {

  private lateinit var currentUser: UserModel

  private val places: List<PlaceModel> = emptyList()

  fun getRecommendPlaces(): Single<List<PlaceModel>> {
    return Single.defer {
      if (places.isNotEmpty()) {
        Single.just(places)
      } else {
        val request = RecommendRequest(
          from = "ratings",
          recommend = "placeID",
          goal = PrepositionRequest().apply {
            put("rating", OperatorRequest.GteOperatorRequest(gte = 1.0))
          },
          limit = 5
        )
        AitoApi.get().getRecommendPlaces(request)
          .map { it.hits }
      }
    }
  }

  fun createUser(user: UserModel = UserModel()): Single<UserModel> {
    return AitoApi.get().createUser(user)
      .doOnSuccess {
        Log.d(ProfileCreateFragment::class.java.simpleName, "New user create: ${it.userId}")

        SessionPreference.userId = it.userId
        currentUser = it
      }
  }

  fun getUser(userId: String): Single<UserModel> {
    val request = SearchRequest(
      from = "users",
      where = PrepositionRequest().apply {
        put("userID", userId)
      }
    )
    return AitoApi.get().getUser(request)
      .map { it.hits.firstOrNull() ?: error("User with id $userId not found!") }
      .doOnSuccess {
        Log.d(ProfileCreateFragment::class.java.simpleName, "Get user: ${it.userId}")
        currentUser = it
      }
      .doOnError {
        Log.d(ProfileCreateFragment::class.java.simpleName, "User with id $userId not found!")
      }
  }
}