package junction.brunz.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import junction.brunz.R
import junction.brunz.data.model.place.PlaceModel

/**
 * Created by Long Vu on 7.11.2020
 */
class PlaceAdapter: RecyclerView.Adapter<PlaceViewHolder>() {

  private var items: List<PlaceModel> = emptyList()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
    return PlaceViewHolder(view)
  }

  override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
    holder.bind(items[position])
  }

  override fun getItemCount(): Int = items.size

  fun update(newItems: List<PlaceModel>) {
    items = newItems
    notifyDataSetChanged()
  }
}