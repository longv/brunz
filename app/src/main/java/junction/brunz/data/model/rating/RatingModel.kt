package junction.brunz.data.model.rating

import com.google.gson.annotations.SerializedName

/**
 * Created by Long Vu on 7.11.2020
 */
data class RatingModel(
  @SerializedName("userID") val userId: String,
  @SerializedName("placeID") val placeId: String,
  @SerializedName("rating") val rating: Int,
  @SerializedName("food_rating") val foodRating: Int,
  @SerializedName("service_rating") val serviceRating: Int
)