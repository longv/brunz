package junction.brunz.data.model.user

import com.google.gson.annotations.SerializedName

/**
 * Created by Long Vu on 7.11.2020
 */
data class UserModel(
  @SerializedName("userID") val userId: String,
  @SerializedName("latitude") val latitude: String,
  @SerializedName("longitude") val longitude: String,
  @SerializedName("smoker") val smoker: String?,
  @SerializedName("drink_level") val drinkLevel: String,
  @SerializedName("dress_preference") val dressPreference: String?,
  @SerializedName("ambience") val ambience: String?,
  @SerializedName("transport") val transport: String?,
  @SerializedName("marital_status") val maritalStatus: String?,
  @SerializedName("hijos") val hijos: String?,
  @SerializedName("birth_year") val birthYear: Int,
  @SerializedName("interest") val interest: String,
  @SerializedName("personality") val personality: String,
  @SerializedName("religion") val religion: String,
  @SerializedName("activity") val activity: String?,
  @SerializedName("color") val color: String,
  @SerializedName("weight") val weight: Int,
  @SerializedName("height") val height: Int,
  @SerializedName("budget") val budget: String?,
  @SerializedName("cuisine") val cuisine: String,
  @SerializedName("payment") val payment: String?
)