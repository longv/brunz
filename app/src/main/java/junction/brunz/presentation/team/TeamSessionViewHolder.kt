package junction.brunz.presentation.team

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import junction.brunz.data.model.session.SessionModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_team_session.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Long Vu on 8.11.2020
 */
class TeamSessionViewHolder(item: View) : RecyclerView.ViewHolder(item), LayoutContainer {

  override val containerView: View? = itemView

  private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

  fun bind(session: SessionModel, onSessionSelected: (SessionModel) -> Unit = {}) {
    itemView.title.text = "Session: ${session.sessionId}"
    itemView.description.text = "Date: ${formatDate(session.createdDate.toLong())}"
    itemView.setOnClickListener {
      onSessionSelected(session)
    }
    itemView.viewBtn.setOnClickListener {
      onSessionSelected(session)
    }
    itemView.container.setBackgroundColor(
      if (session.isOpen) {
        ContextCompat.getColor(itemView.context, android.R.color.white)
      } else {
        ContextCompat.getColor(itemView.context, android.R.color.darker_gray)
      }
    )
    itemView.viewBtn.visibility = if (session.isOpen) {
      View.VISIBLE
    } else {
      View.GONE
    }
  }

  private fun formatDate(millis: Long): String {
    val calendar = Calendar.getInstance().apply { timeInMillis = millis }
    return simpleDateFormat.format(calendar.time)
  }
}