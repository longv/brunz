package junction.brunz.presentation.team

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import junction.brunz.R
import junction.brunz.data.AitoRepository
import kotlinx.android.synthetic.main.fragment_team_suggestion_session.*

/**
 * Created by Long Vu on 7.11.2020
 */
class TeamSuggestionSessionFragment : Fragment() {

  private val teamPlaceSuggestionAdapter: TeamPlaceSuggestionAdapter by lazy { TeamPlaceSuggestionAdapter() }

  private val sessionId: String by lazy {
    arguments?.getString("sessionId") ?: error("Session id not found!")
  }

  private val suggestedPlaceIds: String by lazy {
    arguments?.getString("suggestedPlaceIds") ?: error("Suggested place ids  not found!")
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_team_suggestion_session, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(teamSuggestionsRv) {
      layoutManager = LinearLayoutManager(context)
      adapter = teamPlaceSuggestionAdapter
    }

    populateTeamRecommendPlaces()
  }

  @SuppressLint("CheckResult")
  private fun populateTeamRecommendPlaces() {
    AitoRepository.getTeamSessionRecommendPlaces(suggestedPlaceIds)
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { result, e ->
        teamPlaceSuggestionAdapter.update(result)
        if (e != null) {
          Snackbar.make(requireView(), "An unknown error has occurred", Snackbar.LENGTH_SHORT)
        }
      }
  }
}