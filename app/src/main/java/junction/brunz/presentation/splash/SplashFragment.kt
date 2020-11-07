package junction.brunz.presentation.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.reactivex.android.schedulers.AndroidSchedulers
import junction.brunz.R
import junction.brunz.data.AitoRepository
import junction.brunz.data.SessionPreference
import kotlinx.android.synthetic.main.fragment_splash.*

/**
 * Created by Long Vu on 7.11.2020
 */
class SplashFragment: Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_splash, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val anim = AnimationUtils.loadAnimation(context, R.anim.slide_in_top)
    anim.setAnimationListener(object: Animation.AnimationListener {
      override fun onAnimationStart(animation: Animation) {
      }

      override fun onAnimationEnd(animation: Animation) {
        navigateWithCurrentUser()
      }

      override fun onAnimationRepeat(animation: Animation) {
      }
    })
    logo.startAnimation(anim)
  }

  @SuppressLint("CheckResult")
  fun navigateWithCurrentUser() {
    if (SessionPreference.userId == null) {
      findNavController().navigate(R.id.action_splashFragment_to_profileInitFragment)
    } else {
      AitoRepository.getUser(SessionPreference.userId!!)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { _, e ->
          val actionId = if (e != null) {
            R.id.action_splashFragment_to_profileInitFragment
          } else {
            R.id.action_splashFragment_to_homeFragment
          }
          findNavController().navigate(actionId)
        }
    }
  }
}