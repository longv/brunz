package junction.brunz.data.model.base

import com.google.gson.annotations.SerializedName

/**
 * Created by Long Vu on 7.11.2020
 */
data class RecommendRequest(
  @SerializedName("from") val from: String,
  @SerializedName("get") val get: String,
  @SerializedName("goal") val goal: PrepositionRequest? = null,
  @SerializedName("orderBy") val orderBy: PrepositionRequest? = null,
  @SerializedName("limit") val limit: Int = 10
)