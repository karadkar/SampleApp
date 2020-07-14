package io.github.karadkar.sample.bottom_sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.karadkar.sample.R
import io.github.karadkar.sample.databinding.FragTwoBinding
import io.github.karadkar.sample.utils.createBinding

class SecondBottomSheet : ExpandedBottomSheetFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = R.layout.frag_two.createBinding<FragTwoBinding>(inflater, container)
        return binding.root
    }
}