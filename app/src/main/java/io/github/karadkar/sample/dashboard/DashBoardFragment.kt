package io.github.karadkar.sample.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.github.karadkar.sample.R
import io.github.karadkar.sample.databinding.FragDashboardBinding
import io.github.karadkar.sample.utils.logInfo
import kotlinx.android.synthetic.main.frag_dashboard.*
import org.koin.android.viewmodel.ext.android.viewModel

class DashBoardFragment : Fragment() {
    private lateinit var binding: FragDashboardBinding

    private val viewModel: DashBoardViewModel by viewModel()
    private lateinit var adapter: DashboardListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.frag_dashboard, container, false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = DashboardListAdapter(context = requireContext()) { item ->
            // on click item
            logInfo("dash item clicked $item")
            if (activity is DashboardListContract) {
                (activity as DashboardListContract).onClickDashboardNewItem(item)
            }
        }
        rvDashboard.adapter = adapter

        viewModel.getListItems().observe(this, Observer { items ->
            adapter.submitList(items)
        })
    }
}