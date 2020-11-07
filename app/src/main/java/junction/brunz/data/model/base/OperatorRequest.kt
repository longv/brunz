package junction.brunz.data.model.base

import com.google.gson.annotations.SerializedName

/**
 * Created by Long Vu on 7.11.2020
 */
sealed class OperatorRequest {

  data class AtomicOperatorRequest(
    @SerializedName("\$atomic") val atomic: PrepositionRequest
  ) : OperatorRequest()

  data class AndOperatorRequest(
    @SerializedName("\$and") val and: List<OperatorRequest>
  ) : OperatorRequest()

  data class LteOperatorRequest(
    @SerializedName("\$lte") val lte: Double
  ) : OperatorRequest()

  data class GteOperatorRequest(
    @SerializedName("\$gte") val gte: Double
  ) : OperatorRequest()
}