package junction.brunz.data

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Long Vu on 7.11.2020
 */
object SessionPreference {

  const val BRUNZ_SHARED_PREF = "brunz.shared.pref"

  private const val USER_ID_KEY = "brunz.shared.pref.user.id"

  var userId: String?
    get() = getString(USER_ID_KEY, null)
    set(value) = putString(USER_ID_KEY, value)

  private lateinit var sharedPreferences: SharedPreferences

  fun init(context: Context) {
    sharedPreferences = context.getSharedPreferences(BRUNZ_SHARED_PREF, Context.MODE_PRIVATE)
  }

  private fun getString(key: String, default: String?): String? =
    sharedPreferences.getString(key, default)

  private fun putString(key: String, value: String?, immediately: Boolean = true) {
    sharedPreferences.edit()
      .putString(key, value)
      .apply()
  }
}