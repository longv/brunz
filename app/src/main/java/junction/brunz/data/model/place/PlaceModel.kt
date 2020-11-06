package junction.brunz.data.model.place

import com.google.gson.annotations.SerializedName

/**
 * Created by Long Vu on 7.11.2020
 */
data class PlaceModel(
  @SerializedName("latitude") val latitude: Double,
  @SerializedName("longitude") val longitude: Double,
  @SerializedName("name") val name: String,
  @SerializedName("address") val address: String
)