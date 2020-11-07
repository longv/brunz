package junction.brunz.data.model.place

import com.google.gson.annotations.SerializedName

/**
 * Created by Long Vu on 7.11.2020
 */
data class PlaceModel(
  @SerializedName("cuisine") val cuisine: String?,
  @SerializedName("parking_lot") val parkingLot: String?,
  @SerializedName("latitude") val latitude: Double?,
  @SerializedName("longitude") val longitude: Double?,
  @SerializedName("name") val name: String?,
  @SerializedName("address") val address: String?,
  @SerializedName("city") val city: String?,
  @SerializedName("state") val state: String?,
  @SerializedName("country") val country: String?,
  @SerializedName("zip") val zip: String?,
  @SerializedName("alcohol") val alcohol: String?,
  @SerializedName("smoking_area") val smokingArea: String?,
  @SerializedName("dress_code") val dressCode: String?,
  @SerializedName("accessibility") val accessibility: String?,
  @SerializedName("price") val price: String?,
  @SerializedName("Rambience") val rAmbience: String?,
  @SerializedName("franchise") val franchise: String?,
  @SerializedName("area") val area: String?,
  @SerializedName("other_services") val otherServices: String?
)