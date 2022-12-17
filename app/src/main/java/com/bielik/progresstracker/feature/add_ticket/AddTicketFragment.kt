package com.bielik.progresstracker.feature.add_ticket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bielik.progresstracker.R


class AddTicketFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_ticket, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddTicketFragment()
    }
}