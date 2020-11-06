package junction.brunz.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.reactivex.android.schedulers.AndroidSchedulers
import junction.brunz.R
import junction.brunz.data.AitoRepository
import kotlinx.android.synthetic.main.fragment_splash.*

/**
 * Created by Long Vu on 6.11.2020
 */
class SplashFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_splash, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    emptyProfileBtn.setOnClickListener {
      findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
    }

    profileBtn.setOnClickListener {
      //findNavController().navigate(R.id.action_splashFragment_to_createProfileFragment)

      AitoRepository.getRecommendPlaces()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { result, _ ->
          println("HELOOOOOOOOOOOO")
          println(result)
        }
    }
  }
}