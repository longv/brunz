package junction.brunz.presentation.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.reactivex.android.schedulers.AndroidSchedulers
import junction.brunz.R
import junction.brunz.data.AitoRepository
import junction.brunz.data.model.user.UserModel
import kotlinx.android.synthetic.main.fragment_profile_create.*

/**
 * Created by Long Vu on 6.11.2020
 */
class ProfileCreateFragment: Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_profile_create, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    doneBtn.setOnClickListener {
      val user = UserModel(
        displayName = displayName.text.toString(),
        birthYear = birthYear.text.toString().toInt(),
        cuisine = listOfNotNull(
          if (asianCuisine.isChecked) "asian" else null,
          if (italianCuisine.isChecked) "italian" else null,
          if (mexicanCuisine.isChecked) "mexican" else null,
          if (finnishCuisine.isChecked) "finnish" else null,
          if (americanCuisine.isChecked) "american" else null,
        ).joinToString(separator = ";"),
        diet = listOfNotNull(
          if (veganDiet.isChecked) "vegan" else null,
          if (vegetarianDiet.isChecked) "vegetarian" else null
        ).joinToString(separator = ";").takeIf { it.isNotEmpty() }
      )

      AitoRepository.createUser(user)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { _, _ ->
          findNavController().navigate(R.id.action_profileCreateFragment_to_homeFragment)
        }
    }
  }
}