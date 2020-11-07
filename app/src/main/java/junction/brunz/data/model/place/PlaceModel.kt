package junction.brunz.data.model.place

import com.google.gson.annotations.SerializedName
import kotlin.random.Random

/**
 * Created by Long Vu on 7.11.2020
 */
data class PlaceModel(
  @SerializedName("placeID") val placeId: String,
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
  @SerializedName("ambience") val ambience: String?,
  @SerializedName("franchise") val franchise: String?,
  @SerializedName("area") val area: String?,
  @SerializedName("other_services") val otherServices: String?
) {

  private var imageUrl: String? = null

  fun getImageUrl(): String {
    return imageUrl ?: run {
      val randomIndex = Random.nextInt(0, 7)
      imageUrl = IMAGE_URLS[randomIndex]
      imageUrl!!
    }
  }

  companion object {
    private val IMAGE_URLS: List<String> = listOf(
      "https://images.pexels.com/photos/2575835/pexels-photo-2575835.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
      "https://images.pexels.com/photos/1907098/pexels-photo-1907098.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
      "https://images.pexels.com/photos/3789885/pexels-photo-3789885.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
      "https://images.pexels.com/photos/357573/pexels-photo-357573.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
      "https://images.pexels.com/photos/2253643/pexels-photo-2253643.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
      "https://images.pexels.com/photos/3298604/pexels-photo-3298604.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
      "https://images.pexels.com/photos/3298180/pexels-photo-3298180.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
      "https://images.pexels.com/photos/323682/pexels-photo-323682.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260"
    )
  }
}