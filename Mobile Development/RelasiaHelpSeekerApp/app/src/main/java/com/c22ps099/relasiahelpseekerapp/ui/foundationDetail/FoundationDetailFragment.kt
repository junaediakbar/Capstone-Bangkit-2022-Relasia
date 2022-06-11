package com.c22ps099.relasiahelpseekerapp.ui.foundationDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.data.api.responses.Foundation
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentFormSkillBinding
import com.c22ps099.relasiahelpseekerapp.databinding.FragmentFoundationDetailBinding

class FoundationDetailFragment : Fragment() {
    private var binding: FragmentFoundationDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFoundationDetailBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val foundation = arguments?.getParcelable<Foundation>(EXTRA_FOUNDATION) as Foundation
        binding?.apply {
            ivDetailMission.let {
                Glide.with(it.context)
                    .load(foundation.picture)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    ).placeholder(R.drawable.ic_error)
                    .into(it)
            }
            tvFoundationName.text = foundation.name
            tvFoundationLocation.text = "${foundation.city},${foundation.province}"
            tvFoundationPhone.text = foundation.phone
        }
    }

    companion object {
        const val EXTRA_FOUNDATION = "extra_foundation"
    }

}