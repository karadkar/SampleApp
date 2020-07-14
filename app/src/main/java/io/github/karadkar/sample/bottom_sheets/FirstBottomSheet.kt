package io.github.karadkar.sample.bottom_sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.karadkar.sample.R
import io.github.karadkar.sample.databinding.FragOneBinding
import io.github.karadkar.sample.utils.createBinding

class FirstBottomSheet : ExpandedBottomSheetFragment() {
    private lateinit var binding: FragOneBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = R.layout.frag_one.createBinding(layoutInflater, container)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnClickMe.setOnClickListener {
            SecondBottomSheet().show(childFragmentManager, "frag-two")
        }
    }
}