package junction.brunz.data.model.base

import com.google.gson.annotations.SerializedName

/**
 * Created by Long Vu on 7.11.2020
 */
sealed class OperatorRequest {

  data class AndOperatorRequestModel(
    @SerializedName("\$and") val and: List<OperatorRequest>
  ) : OperatorRequest()

  data class LteOperatorRequestModel(
    @SerializedName("\$lte") val lte: Double
  ) : OperatorRequest()

  data class GteOperatorRequestModel(
    @SerializedName("\$gte") val gte: Double
  ) : OperatorRequest()
}