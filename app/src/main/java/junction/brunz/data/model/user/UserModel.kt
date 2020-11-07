package junction.brunz.data.model.user

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by Long Vu on 7.11.2020
 */
data class UserModel(
  @SerializedName("userID") val userId: String = UUID.randomUUID().toString(),
  @SerializedName("groupID") val groupId: String? = null,
  @SerializedName("display_name") val displayName: String? = null,
  @SerializedName("latitude") val latitude: Double = 22.15,
  @SerializedName("longitude") val longitude: Double = -101.02,
  @SerializedName("smoker") val smoker: String? = "none",
  @SerializedName("drink_level") val drinkLevel: String = "none",
  @SerializedName("dress_preference") val dressPreference: String? = null,
  @SerializedName("ambience") val ambience: String? = null,
  @SerializedName("transport") val transport: String? = null,
  @SerializedName("marital_status") val maritalStatus: String? = null,
  @SerializedName("hijos") val hijos: String? = null,
  @SerializedName("birth_year") val birthYear: Int = -1,
  @SerializedName("interest") val interest: String = "none",
  @SerializedName("personality") val personality: String = "none",
  @SerializedName("religion") val religion: String = "none",
  @SerializedName("activity") val activity: String? = "none",
  @SerializedName("color") val color: String = "green",
  @SerializedName("weight") val weight: Double = 65.0,
  @SerializedName("height") val height: Double = 1.8,
  @SerializedName("budget") val budget: String? = "medium",
  @SerializedName("cuisine") val cuisine: String = "",
  @SerializedName("diet") val diet: String? = null,
  @SerializedName("favourite") val favorite: String? = null,
  @SerializedName("payment") val payment: String? = null
)