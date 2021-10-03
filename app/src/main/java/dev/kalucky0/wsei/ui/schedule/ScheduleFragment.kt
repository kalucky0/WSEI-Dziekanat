package dev.kalucky0.wsei.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import dev.kalucky0.wsei.R
import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.data.web.ScheduleData

class ScheduleFragment : Fragment() {

    companion object {
        fun newInstance() = ScheduleFragment()
    }

    private lateinit var schedulePagerAdapter: SchedulePagerAdapter
    private lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ScheduleData {
            Utils.schedule = it
            activity?.runOnUiThread {
                schedulePagerAdapter = SchedulePagerAdapter(childFragmentManager)
                viewPager = view.findViewById(R.id.schedule)
                viewPager.adapter = schedulePagerAdapter
                val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)
                tabLayout.setupWithViewPager(viewPager)
            }
        }
    }
}
