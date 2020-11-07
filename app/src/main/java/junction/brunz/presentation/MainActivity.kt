package junction.brunz.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import junction.brunz.R
import junction.brunz.data.SessionPreference

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
      window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
      window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    SessionPreference.init(this)
  }
}