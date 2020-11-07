package junction.brunz.data.model.session

import com.google.gson.annotations.SerializedName

/**
 * Created by Long Vu on 8.11.2020
 */
data class SessionVoteModel(
  @SerializedName("sessionID") val sessionId: String,
  @SerializedName("userID") val userId: String,
  @SerializedName("vote") val vote: String
)