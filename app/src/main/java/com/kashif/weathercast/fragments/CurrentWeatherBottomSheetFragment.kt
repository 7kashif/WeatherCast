package com.kashif.weathercast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kashif.weathercast.Constants
import com.kashif.weathercast.R
import com.kashif.weathercast.databinding.CurrentWeatherBottomSheetBinding
import com.kashif.weathercast.room.FavoritePlace
import com.kashif.weathercast.viewModel.FavoritePlacesViewModel
import kotlinx.coroutines.launch

class CurrentWeatherBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: CurrentWeatherBottomSheetBinding
    private val favoritePlaceViewModel: FavoritePlacesViewModel by activityViewModels()
    private val args: CurrentWeatherBottomSheetFragmentArgs by navArgs()

    override fun onStart() {
        super.onStart()
        setStyle(STYLE_NO_FRAME, R.style.CustomBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CurrentWeatherBottomSheetBinding.inflate(inflater)
        val response = args.response
        binding.response = response
        binding.constant = Constants
        setUpViews()

        return binding.root
    }

    private fun setUpViews() {
        val latLng = LatLng(
            args.response.coord.lat,
            args.response.coord.lon
        )

        lifecycleScope.launch {
            if (favoritePlaceViewModel.getFavoritePlaceByLatLng(latLng) != null)
                binding.cbAddFavorite.isChecked = true
        }

        binding.cbAddFavorite.setOnCheckedChangeListener { button, isChecked ->
            if (button.isPressed) {
                if (isChecked) {
                    val place = FavoritePlace(latLng = latLng)
                    favoritePlaceViewModel.addFavoritePlace(place)
                    Toast.makeText(activity, "Favorite place added.", Toast.LENGTH_LONG).show()
                } else {
                    favoritePlaceViewModel.deleteSingleFavoritePlace(latLng)
                    Toast.makeText(activity, "Removed favorite place.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}