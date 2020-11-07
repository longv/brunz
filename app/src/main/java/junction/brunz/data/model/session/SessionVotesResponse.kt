package junction.brunz.data.model.session

import com.google.gson.annotations.SerializedName

/**
 * Created by Long Vu on 8.11.2020
 */
data class SessionVotesResponse(
  @SerializedName("offset") val offset: Int,
  @SerializedName("total") val total: Int,
  @SerializedName("hits") val hits: List<SessionVoteModel>
)