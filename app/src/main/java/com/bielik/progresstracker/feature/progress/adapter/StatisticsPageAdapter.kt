package com.bielik.progresstracker.feature.progress.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bielik.progresstracker.feature.persistence_task_list.PersistenceTaskListFragment
import com.bielik.progresstracker.feature.progress.StatisticsFragment
import com.bielik.progresstracker.feature.progress_task_list.ProgressTaskListFragment

private const val TAB_COUNT: Int = 2

class StatisticsPageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = TAB_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            StatisticsFragment.PERSISTENCE_POSITION -> PersistenceTaskListFragment()
            StatisticsFragment.PROGRESS_POSITION -> ProgressTaskListFragment()
            else -> throw IllegalArgumentException()
        }
    }
}
