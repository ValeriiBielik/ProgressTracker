package com.bielik.progresstracker.feature.progress

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.bielik.progresstracker.R
import com.bielik.progresstracker.base.BaseBindingFragment
import com.bielik.progresstracker.databinding.FragmentStatisticsBinding
import com.bielik.progresstracker.feature.progress.adapter.StatisticsPageAdapter
import com.bielik.progresstracker.utils.extensions.getThemeColor
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : BaseBindingFragment<FragmentStatisticsBinding, StatisticsViewModel>() {
    override val viewModel by viewModels<StatisticsViewModel>()

    private val defaultTabTextColor by lazy { requireActivity().getThemeColor(com.google.android.material.R.attr.colorOnBackground) }
    private val selectedTabTextColor by lazy { requireActivity().getThemeColor(com.google.android.material.R.attr.colorSurfaceVariant) }
    private val defaultBackgroundColor by lazy { requireActivity().getThemeColor(com.google.android.material.R.attr.backgroundColor) }

    override fun initUI() {
        setupTabs()
    }

    private fun setupTabs() = withBinding {
        tabLayoutStatistics.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.let {
                    it.findViewById<LinearLayout>(R.id.layoutTabBtn)?.setBackgroundResource(R.drawable.bg_btn_on_surface_variant_rounded)
                    it.findViewById<TextView>(R.id.textViewTab)?.setTextColor(selectedTabTextColor)
                }
                viewModel.onTabChanged(tab?.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.let {
                    it.findViewById<LinearLayout>(R.id.layoutTabBtn)?.setBackgroundColor(defaultBackgroundColor)
                    it.findViewById<TextView>(R.id.textViewTab)?.setTextColor(defaultTabTextColor)
                }
            }
        })
        viewPagerStatistics.adapter = StatisticsPageAdapter(this@StatisticsFragment)
        TabLayoutMediator(tabLayoutStatistics, viewPagerStatistics) { tab, position ->
            tab.setCustomView(R.layout.item_tab)
            val textRes = when (position) {
                PERSISTENCE_POSITION -> R.string.label_persistence
                PROGRESS_POSITION -> R.string.label_progress
                else -> throw IllegalArgumentException()
            }
            tab.customView?.findViewById<TextView>(R.id.textViewTab)?.setText(textRes)
        }.attach()
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentStatisticsBinding.inflate(inflater, container, attachToRoot)

    companion object {
        const val PERSISTENCE_POSITION = 0
        const val PROGRESS_POSITION = 1
    }
}