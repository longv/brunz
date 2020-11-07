package junction.brunz.data.model.base

import com.google.gson.annotations.SerializedName

/**
 * Created by Long Vu on 7.11.2020
 */
data class SearchRequest(
  @SerializedName("from") val from: String,
  @SerializedName("where") val where: PrepositionRequest,
  @SerializedName("orderBy") val orderBy: String? = null,
  @SerializedName("limit") val limit: Int = 10
)