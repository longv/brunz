package junction.brunz.presentation.team

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import junction.brunz.R
import junction.brunz.data.model.place.PlaceModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_place.view.*
import kotlinx.android.synthetic.main.item_team_place_suggestion.*
import kotlinx.android.synthetic.main.item_team_place_suggestion.view.*
import kotlinx.android.synthetic.main.item_team_place_suggestion.view.description
import kotlinx.android.synthetic.main.item_team_place_suggestion.view.placeImg
import kotlinx.android.synthetic.main.item_team_place_suggestion.view.title

/**
 * Created by Long Vu on 7.11.2020
 */
class TeamPlaceSuggestionViewHolder(item: View): RecyclerView.ViewHolder(item), LayoutContainer {

  override val containerView: View? = itemView

  fun bind(
    place: PlaceModel,
    isVoted: Boolean,
    onVoteClick: (PlaceModel) -> Unit
  ) {
    val price =  when (place.price) {
      "low" -> "$"
      "medium" -> "$$"
      "high" -> "$$$"
      else -> "$$"
    }

    Picasso.get().load(place.getImageUrl()).fit().into(itemView.placeImg)
    itemView.title.text = place.name.orEmpty().capitalize()
    itemView.description.text = "${place.cuisine} cuisine"
    itemView.review.text = "$price - 4.5 ratings"
    itemView.voteBtn.imageTintList = if (isVoted) {
      ColorStateList.valueOf(ContextCompat.getColor(itemView.context, R.color.colorPrimary))
    } else {
      ColorStateList.valueOf(ContextCompat.getColor(itemView.context, R.color.eclipse))
    }
    itemView.voteBtn.setOnClickListener { onVoteClick(place) }
  }
}