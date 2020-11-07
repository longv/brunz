package junction.brunz.presentation.team

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import junction.brunz.R
import junction.brunz.data.model.session.SessionModel

/**
 * Created by Long Vu on 8.11.2020
 */
class TeamSessionAdapter(
  private val onSessionSelected: (SessionModel) -> Unit = {}
) : RecyclerView.Adapter<TeamSessionViewHolder>() {

  private var items: List<SessionModel> = emptyList()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamSessionViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_team_session, parent, false)
    return TeamSessionViewHolder(view)
  }

  override fun onBindViewHolder(holder: TeamSessionViewHolder, position: Int) {
    val currentModel = items[position]
    holder.bind(currentModel, onSessionSelected)
  }

  override fun getItemCount(): Int = items.size

  fun update(newItems: List<SessionModel>) {
    items = newItems
    notifyDataSetChanged()
  }
}