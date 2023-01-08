package com.bielik.progresstracker.feature.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bielik.progresstracker.R
import com.bielik.progresstracker.base.BaseBindingFragment
import com.bielik.progresstracker.databinding.FragmentHomeBinding
import com.bielik.progresstracker.feature.home.adapter.TicketsAdapter
import com.bielik.progresstracker.feature.home.model.SetupUiData
import com.bielik.progresstracker.utils.extensions.getWeekPageTitle
import com.bielik.progresstracker.utils.extensions.onClick
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.YearMonth

@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding, HomeViewModel>() {
    override val viewModel by viewModels<HomeViewModel>()

    private val adapter: TicketsAdapter by lazy { TicketsAdapter(onItemClick = { onTicketClick(it) }) }
    private lateinit var dayBinder: DayViewBinder

    override fun initUI() {
        setupRecyclerView()
        implementListeners()
        subscribe()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchTickets()
    }

    private fun setupRecyclerView() = withBinding {
        rvTickets.adapter = adapter
    }

    private fun implementListeners() = withBinding {
        btnAdd.onClick { findNavController().navigate(HomeFragmentDirections.navigateToAddTicketFragment()) }
        swipeRefreshLayout.setOnRefreshListener { refreshData() }
    }

    private fun subscribe() {
        viewModel.setupFlow.observeWhenResumed { setupUi(it) }
        viewModel.ticketsFlow.observeWhenResumed { adapter.clearAndSetData(it) }
        viewModel.onDateSelectedEvent.observeWhenResumed { onDateSelected(it.first, it.second) }
    }

    private fun onDateSelected(oldDate: LocalDate, newDate: LocalDate) = withBinding {
        dayBinder.setSelectedDate(newDate)
        weekCalendarView.notifyDateChanged(oldDate)
        weekCalendarView.notifyDateChanged(newDate)
    }

    private fun setupUi(uiData: SetupUiData) = withBinding {
        if (!uiData.userName.isNullOrBlank()) {
            headerView.setupView(getString(R.string.format_user_greeting, uiData.userName), showMonthTitle = true)
        }
        setupCalendar(uiData.selectedDate, uiData.currentMonth)
    }

    private fun setupCalendar(selectedDate: LocalDate, currentMonth: YearMonth) = withBinding {
        dayBinder = DayViewBinder(selectedDate, onClick = { viewModel.onCalendarDayClick(it) })
        weekCalendarView.dayBinder = dayBinder
        weekCalendarView.weekScrollListener = { weekDays -> headerView.updateMonthTitle(getWeekPageTitle(weekDays)) }
        weekCalendarView.setup(
            currentMonth.atStartOfMonth(),
            currentMonth.plusMonths(1).atEndOfMonth(),
            firstDayOfWeekFromLocale(),
        )
        weekCalendarView.scrollToDate(selectedDate)
    }

    private fun refreshData() = withBinding {
        viewModel.fetchTickets()
        swipeRefreshLayout.isRefreshing = false
    }

    private fun onTicketClick(id: Long) {
        findNavController().navigate(HomeFragmentDirections.navigateToTicketDetailsFragment(id))
    }

    override fun attachBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) =
        FragmentHomeBinding.inflate(inflater, container, false)
}