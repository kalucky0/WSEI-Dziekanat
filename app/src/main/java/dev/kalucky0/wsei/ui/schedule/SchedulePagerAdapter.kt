package dev.kalucky0.wsei.ui.schedule

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import dev.kalucky0.wsei.data.models.Schedule
import kotlinx.datetime.LocalDate
import java.time.format.TextStyle
import java.util.*

class SchedulePagerAdapter(
    fm: FragmentManager,
    private val days: List<LocalDate>,
    private val schedule: List<Schedule>
) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int = days.size

    override fun getItem(i: Int): Fragment {
        val fragment = ScheduleDayFragment(schedule, days[i].toString())
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return days[position].dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }
}