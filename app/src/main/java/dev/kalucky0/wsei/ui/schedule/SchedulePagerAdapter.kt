package dev.kalucky0.wsei.ui.schedule

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.kalucky0.wsei.data.models.Schedule
import kotlinx.datetime.LocalDate

class SchedulePagerAdapter(
    fm: Fragment,
    private val days: List<LocalDate>,
    private val schedule: List<Schedule>
) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = days.size

    override fun createFragment(i: Int): Fragment {
        return ScheduleDayFragment(schedule, days[i].toString())
    }
}