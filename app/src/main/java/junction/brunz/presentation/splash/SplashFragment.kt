package junction.brunz.presentation.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import junction.brunz.R
import kotlinx.android.synthetic.main.fragment_splash.*

/**
 * Created by Long Vu on 6.11.2020
 */
class SplashFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_splash, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    emptyProfileBtn.setOnClickListener {
      findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
    }

    profileBtn.setOnClickListener {
      findNavController().navigate(R.id.action_splashFragment_to_createProfileFragment)
    }
  }
}