package com.c22ps099.relasiahelpseekerapp.ui.form

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentFormLocationBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
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

    private val callback = OnMapReadyCallback { googleMap ->

        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
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

        val autocompleteFragment: AutocompleteSupportFragment? =
            childFragmentManager.findFragmentById(R.id.et_autosearch_location) as AutocompleteSupportFragment?

        autocompleteFragment?.setTypeFilter(TypeFilter.ADDRESS)
        autocompleteFragment?.setCountries("ID")
        autocompleteFragment?.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        autocompleteFragment?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                address = place
                // TODO: Get info about the selected place.
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
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding?.apply {
            btnSaveLoc.setOnClickListener {
                val navigateAction = FormLocationFragmentDirections
                    .actionFormLocationFragmentToFormFragment()
                navigateAction.location = "${address?.name +" "+ etDetailLoc.text}"
                navigateAction.latitude = address?.latLng?.latitude.toString()
                navigateAction.longitude = address?.latLng?.longitude.toString()
                findNavController().navigate(navigateAction)
            }
        }
    }
}