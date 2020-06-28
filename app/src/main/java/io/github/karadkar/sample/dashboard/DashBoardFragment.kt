package io.github.karadkar.sample.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import io.github.karadkar.sample.R
import io.github.karadkar.sample.databinding.FragDashboardBinding

class DashBoardFragment : Fragment() {
    private lateinit var binding: FragDashboardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.frag_dashboard, container, false
        )
        return binding.root
    }
}