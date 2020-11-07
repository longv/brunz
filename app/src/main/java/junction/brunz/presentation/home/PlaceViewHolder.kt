package junction.brunz.presentation.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import junction.brunz.data.model.place.PlaceModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_place.view.*

class PlaceViewHolder(item: View): RecyclerView.ViewHolder(item), LayoutContainer {

  override val containerView: View? = itemView

  fun bind(place: PlaceModel) {
    Picasso.get().load(place.getImageUrl()).fit().into(itemView.placeImg)
    itemView.title.text = place.name.orEmpty().capitalize()
    itemView.description.text = "${place.cuisine} cuisine"
    itemView.address.text = "Address: ${place.address}"
    itemView.price.text = when (place.price) {
      "low" -> "$"
      "medium" -> "$$"
      "high" -> "$$$"
      else -> "$$"
    }
  }
}