package com.c22ps099.relasiahelpseekerapp.ui.form

import android.content.ContentValues
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.SharedElementCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentFormLocationBinding
import com.c22ps099.relasiahelpseekerapp.ui.login.LoginFragment.Companion.TAG
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import java.util.*

class FormLocationFragment : Fragment() {

    private var binding: FragmentFormLocationBinding? = null

    private var address : Place? = null

    private lateinit var mMap : GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->
        setMapStyle(googleMap)
        val sydney = LatLng(-34.0, 151.0)
        mMap=googleMap
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentFormLocationBinding.inflate(inflater, container, false)

        if (!Places.isInitialized()) {
            Places.initialize(activity, getString(R.string.google_maps_key), Locale.US);
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        val autocompleteFragment: AutocompleteSupportFragment? =
            childFragmentManager.findFragmentById(R.id.et_autosearch_location) as AutocompleteSupportFragment?

        autocompleteFragment?.setCountries("ID")
        autocompleteFragment?.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        autocompleteFragment?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                address = place
                // TODO: Get info about the selected place.
                binding?.tvLocAddressFromMap?.text = address?.name.toString()
                mMap.addMarker(MarkerOptions().position(address?.latLng!!).title("Your Picked"))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(address?.latLng!!))
                Log.i(ContentValues.TAG, "Place: " + place.name + ", " + place.id)
                Toast.makeText(activity, "Place: " + place.name + ", " + place.id,Toast.LENGTH_SHORT).show()
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(ContentValues.TAG, "An error occurred: $status")
            }
        })

        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            btnSaveLoc.setOnClickListener {
                findNavController().previousBackStackEntry?.savedStateHandle?.set("loc", "${address?.name +" "+etDetailLoc.text}")
                findNavController().popBackStack()
            }
        }
    }
    private fun setMapStyle(map: GoogleMap) {
        try {
            context?.let {
                val success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        it,
                        R.raw.map_style
                    )
                )

                if (!success) {
                    Log.e(TAG, "Style parsing failed.")
                }
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }


}