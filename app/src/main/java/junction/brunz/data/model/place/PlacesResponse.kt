package junction.brunz.data.model.place

import com.google.gson.annotations.SerializedName

/**
 * Created by Long Vu on 7.11.2020
 */
data class PlacesResponse(
  @SerializedName("offset") val offset: Int,
  @SerializedName("total") val total: Int,
  @SerializedName("hits") val hits: List<PlaceModel>
)