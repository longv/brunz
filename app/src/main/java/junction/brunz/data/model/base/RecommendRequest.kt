package junction.brunz.data.model.base

import com.google.gson.annotations.SerializedName

/**
 * Created by Long Vu on 7.11.2020
 */
data class RecommendRequest(
  @SerializedName("from") val from: String,
  @SerializedName("recommend") val recommend: String,
  @SerializedName("goal") val goal: Map<String, OperatorRequest>,
  @SerializedName("limit") val limit: Int
)