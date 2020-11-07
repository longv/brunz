package junction.brunz.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import junction.brunz.R
import junction.brunz.data.AitoRepository

/**
 * Created by Long Vu on 6.11.2020
 */
class HomeFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_home, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    AitoRepository.getRecommendPlaces()
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { result, e ->
        println("MOMENNTTT")
        println(result)
      }
  }
}