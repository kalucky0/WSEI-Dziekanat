package dev.kalucky0.wsei.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.kalucky0.wsei.R
import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.data.models.Schedule
import kotlinx.datetime.LocalDate
import java.time.format.TextStyle
import java.util.*

class ScheduleFragment : Fragment() {
    private lateinit var schedulePagerAdapter: SchedulePagerAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Thread {
            val days: List<LocalDate> = Utils.db?.scheduleDao()!!.getAllDates()
            val schedule: List<Schedule> = Utils.db?.scheduleDao()!!.getAll()

            activity?.runOnUiThread {
                schedulePagerAdapter = SchedulePagerAdapter(this, days, schedule)
                viewPager = view.findViewById(R.id.schedule)
                viewPager.adapter = schedulePagerAdapter
                val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text =
                        days[position].dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
                }.attach()
            }
        }.start()
    }
}
