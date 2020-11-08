package junction.brunz.presentation.team

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import junction.brunz.R
import junction.brunz.data.AitoRepository
import junction.brunz.data.model.place.PlaceModel
import kotlinx.android.synthetic.main.fragment_team_suggestion_session.*

/**
 * Created by Long Vu on 7.11.2020
 */
class TeamSuggestionSessionFragment : Fragment() {

  private val teamPlaceSuggestionAdapter: TeamPlaceSuggestionAdapter by lazy {
    TeamPlaceSuggestionAdapter(onVoted = {
      doneBtn.isEnabled = true
    })
  }

  private val sessionId: String by lazy {
    arguments?.getString("sessionId") ?: error("Session id not found!")
  }

  private val suggestedPlaceIds: String by lazy {
    arguments?.getString("suggestedPlaceIds") ?: error("Suggested place ids  not found!")
  }

  private val chosenPlaceId: String? by lazy {
    arguments?.getString("chosenPlaceId")
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_team_suggestion_session, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    populateTeamRecommendPlaces()
  }

  @SuppressLint("CheckResult")
  private fun populateTeamRecommendPlaces() {
    AitoRepository.getTeamSessionRecommendPlaces(suggestedPlaceIds)
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { result, e ->
        if (chosenPlaceId == null) {
          showSuggestions(result)
        } else {
          result.find { it.placeId == chosenPlaceId }?.also {
            showWinner(it)
          }
        }

        if (e != null) {
          Snackbar.make(requireView(), "An unknown error has occurred", Snackbar.LENGTH_SHORT)
        }
      }
  }

  @SuppressLint("CheckResult")
  private fun doVote() {
    AitoRepository.voteForTeamSession(sessionId, teamPlaceSuggestionAdapter.votedItem!!.placeId)
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeBy(
        onComplete = {
          doneBtn.text = "Waiting for others"
          doneBtn.setOnClickListener {
            checkResult()
          }
          checkResult()
        },
        onError = {
          Snackbar.make(requireView(), "An unknown error has occurred", Snackbar.LENGTH_SHORT)
        }
      )
  }

  @SuppressLint("CheckResult")
  private fun checkResult() {
    AitoRepository.getSessionResult(sessionId)
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeBy(
        onSuccess = {
          showWinner(it)
        },
        onComplete = {
        },
        onError = {
          Snackbar.make(requireView(), "An unknown error has occurred", Snackbar.LENGTH_SHORT)
        }
      )
  }

  private fun showSuggestions(places: List<PlaceModel>) {
    viewSwitcher.displayedChild = 0

    with(teamSuggestionsRv) {
      layoutManager = LinearLayoutManager(context)
      adapter = teamPlaceSuggestionAdapter
    }

    doneBtn.setOnClickListener {
      doVote()
    }

    teamPlaceSuggestionAdapter.update(places)
  }

  private fun showWinner(place: PlaceModel) {
    viewSwitcher.displayedChild = 1

    Picasso.get().load(place.getImageUrl()).fit().into(placeImg)
    placeTitle.text = place.name.orEmpty()
    description.text = "${place.cuisine} cuisine"
  }
}