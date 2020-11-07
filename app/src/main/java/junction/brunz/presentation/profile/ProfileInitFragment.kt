package junction.brunz.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.reactivex.android.schedulers.AndroidSchedulers
import junction.brunz.R
import junction.brunz.data.AitoRepository
import kotlinx.android.synthetic.main.fragment_profile_init.*

/**
 * Created by Long Vu on 6.11.2020
 */
class ProfileInitFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_profile_init, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    emptyProfileBtn.setOnClickListener {
      AitoRepository.createUser()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { _, _ ->
          findNavController().navigate(R.id.action_profileInitFragment_to_homeFragment)
        }
    }

    profileBtn.setOnClickListener {
      findNavController().navigate(R.id.action_profileInitFragment_to_profileCreateFragment)
    }
  }
}