package junction.brunz.data

import io.reactivex.Single
import junction.brunz.data.model.base.OperatorRequest
import junction.brunz.data.model.base.RecommendRequest
import junction.brunz.data.model.place.PlaceModel
import junction.brunz.data.network.AitoApi

/**
 * Created by Long Vu on 7.11.2020
 */
object AitoRepository {

  private val places: List<PlaceModel> = emptyList()

  fun getRecommendPlaces(): Single<List<PlaceModel>> {
    return Single.defer {
      if (places.isNotEmpty()) {
        Single.just(places)
      } else {
        val request = RecommendRequest(
          from = "ratings",
          recommend = "placeID",
          goal = mapOf(
            "rating" to OperatorRequest.GteOperatorRequestModel(gte = 1.0)
          ),
          limit = 5
        )
        AitoApi.get().getRecommendPlaces(request)
          .map { it.hits }
      }
    }
  }
}