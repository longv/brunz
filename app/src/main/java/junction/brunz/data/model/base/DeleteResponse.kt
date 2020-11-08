package junction.brunz.data.model.base

import com.google.gson.annotations.SerializedName

/**
 * Created by Long Vu on 8.11.2020
 */
data class DeleteResponse(
  @SerializedName("total") val total: Int
)