package junction.brunz.data.model.session

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by Long Vu on 8.11.2020
 */
data class SessionModel(
  @SerializedName("sessionID") val sessionId: String = UUID.randomUUID().toString(),
  @SerializedName("teamID") val groupId: String,
  @SerializedName("createdDate") val createdDate: String = System.currentTimeMillis().toString(),
  @SerializedName("isOpen") val isOpen: Boolean,
  @SerializedName("options") val suggestedPlaceIds: String,
  @SerializedName("chosen") val chosenPlaceId: String? = null,
)