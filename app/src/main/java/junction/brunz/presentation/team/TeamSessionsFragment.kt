package junction.brunz.presentation.team

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import junction.brunz.R
import junction.brunz.data.AitoRepository
import kotlinx.android.synthetic.main.fragment_team_sessions.*

/**
 * Created by Long Vu on 8.11.2020
 */
class TeamSessionsFragment : Fragment() {

  private val sessionsAdapter: TeamSessionAdapter by lazy {
    TeamSessionAdapter(onSessionSelected = {
      val bundle = bundleOf(
        "sessionId" to it.sessionId,
        "suggestedPlaceIds" to it.suggestedPlaceIds,
        "chosenPlaceId" to it.chosenPlaceId
      )
      findNavController().navigate(R.id.action_teamSessionsFragment_to_teamSuggestionSessionFragment, bundle)
    })
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_team_sessions, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    with(sessionsRv) {
      layoutManager = LinearLayoutManager(context)
      adapter = sessionsAdapter
    }

    populateTeamSessions()
  }

  @SuppressLint("CheckResult")
  private fun populateTeamSessions() {
    AitoRepository.getTeamSessions()
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { result, e ->
        sessionsAdapter.update(result)
        if (e != null) {
          Snackbar.make(requireView(), "An unknown error has occurred", Snackbar.LENGTH_SHORT)
        }
      }
  }
}