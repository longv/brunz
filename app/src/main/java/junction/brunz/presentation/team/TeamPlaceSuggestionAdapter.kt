package junction.brunz.presentation.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import junction.brunz.R
import junction.brunz.data.model.place.PlaceModel

/**
 * Created by Long Vu on 7.11.2020
 */
class TeamPlaceSuggestionAdapter : RecyclerView.Adapter<TeamPlaceSuggestionViewHolder>() {

  private var items: List<PlaceModel> = emptyList()

  private var votedItem: PlaceModel? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamPlaceSuggestionViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_team_place_suggestion, parent, false)
    return TeamPlaceSuggestionViewHolder(view)
  }

  override fun onBindViewHolder(holder: TeamPlaceSuggestionViewHolder, position: Int) {
    val currentModel = items[position]
    holder.bind(currentModel, currentModel.placeId == votedItem?.placeId) {
      votedItem = it
      notifyDataSetChanged()
    }
  }

  override fun getItemCount(): Int = items.size

  fun update(newItems: List<PlaceModel>) {
    items = newItems
    notifyDataSetChanged()
  }
}