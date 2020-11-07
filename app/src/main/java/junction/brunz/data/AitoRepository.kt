package junction.brunz.data

import android.util.Log
import io.reactivex.Single
import junction.brunz.data.model.base.PrepositionRequest
import junction.brunz.data.model.base.RecommendRequest
import junction.brunz.data.model.base.SearchRequest
import junction.brunz.data.model.place.PlaceModel
import junction.brunz.data.model.user.UserModel
import junction.brunz.data.network.AitoApi
import junction.brunz.presentation.profile.ProfileCreateFragment

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
          get = "placeID",
          orderBy = PrepositionRequest.MultiplyOperatorRequest(
            multiply = listOf(
              PrepositionRequest.ProbabilityOperatorRequest(
                p = PrepositionRequest.ContextOperatorRequest(
                  context = PrepositionRequest.QueryPrepositionRequest(
                    "rating", PrepositionRequest.AtomicOperatorRequest(
                      atomic = PrepositionRequest.CompositePrepositionRequest(
                        PrepositionRequest.GtOperatorRequest(
                          gt = 0.5
                        ),
                        PrepositionRequest.LteOperatorRequest(
                          lte = 2.5
                        )
                      )
                    ))
                )
              ),
              PrepositionRequest.SimilarityOperatorRequest(
                similarity = PrepositionRequest.AtomicOperatorRequest(
                  atomic = PrepositionRequest.CompositePrepositionRequest(
                    *getCurrentUserCuisinePreposition()
                  )
                )
              )
            )
          ),
          limit = 5
        )
        AitoApi.get().getRecommendPlaces(request)
          .map { it.hits }
      }
    }
  }

  private fun getCurrentUserCuisinePreposition(): Array<PrepositionRequest> {
    val cuisines = currentUser.cuisine.split(";")
      .filter { it.isNotEmpty() }
      .takeIf { it.isNotEmpty() }
      ?: listOf("International")

    return cuisines.map {
      PrepositionRequest.QueryPrepositionRequest(
        "cuisine", PrepositionRequest.HasOperatorRequest(
          has = it
        )
      )
    }.toTypedArray()
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
      where = PrepositionRequest.QueryPrepositionRequest("userID", userId)
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