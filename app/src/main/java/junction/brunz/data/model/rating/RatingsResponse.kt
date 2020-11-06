package junction.brunz.data.model.rating

import com.google.gson.annotations.SerializedName

/**
 * Created by Long Vu on 7.11.2020
 */
data class RatingsResponse(
  @SerializedName("offset") val offset: Int,
  @SerializedName("total") val total: Int,
  @SerializedName("hits") val hits: List<RatingModel>
)