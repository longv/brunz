package junction.brunz.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import junction.brunz.R
import junction.brunz.data.AitoRepository
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * Created by Long Vu on 6.11.2020
 */
class HomeFragment : Fragment() {

  private val placeAdapter: PlaceAdapter by lazy { PlaceAdapter() }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_home, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(placesRv) {
      layoutManager = GridLayoutManager(context, 2)
      adapter = placeAdapter
    }

    teamBtn.setOnClickListener {
      findNavController().navigate(R.id.action_homeFragment_to_teamSessionsFragment)
    }

    populateRecommendPlaces()
  }

  @SuppressLint("CheckResult")
  private fun populateRecommendPlaces() {
    AitoRepository.getPersonalRecommendPlaces()
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { result, e ->
        placeAdapter.update(result)
        if (e != null) {
          Snackbar.make(requireView(), "An unknown error has occurred", Snackbar.LENGTH_SHORT)
        }
      }
  }
}