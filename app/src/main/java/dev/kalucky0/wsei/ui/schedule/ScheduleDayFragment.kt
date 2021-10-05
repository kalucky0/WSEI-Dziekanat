package dev.kalucky0.wsei.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.kalucky0.wsei.R
import dev.kalucky0.wsei.Utils.Companion.db
import dev.kalucky0.wsei.Utils.Companion.toPixels
import dev.kalucky0.wsei.data.models.Schedule
import kotlin.math.*

class ScheduleDayFragment(private val schedule: List<Schedule>, private val day: String) :
    Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_schedule_day, container, false)
    }

    private var startHour: Int = 8
    private var endHour: Int = 24
    private lateinit var hoursView: RecyclerView
    private lateinit var activitiesView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        hoursView = view.findViewById(R.id.hours)
        hoursView.layoutManager = LinearLayoutManager(context)
        hoursView.layoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean = false
        }
        activitiesView = view.findViewById(R.id.activities)
        activitiesView.layoutManager = LinearLayoutManager(context)
        activitiesView.layoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean = false
        }

        val activities: List<Schedule> = schedule.filter { it.day.toString() == day }
        setupSchedule(activities)
    }

    private fun setupSchedule(activities: List<Schedule>) {
        startHour = max(floor(activities[0].timeFrom).roundToInt() - 2, 8)
        endHour = min(ceil(activities.last().timeTo).roundToInt() + 2, 24)

        val hours: ArrayList<String> = ArrayList()
        for (i in startHour..endHour) hours.add("$i:00")

        hoursView.layoutParams.height = toPixels(80f, context) * (endHour - startHour)
        activitiesView.layoutParams.height = toPixels(80f, context) * (endHour - startHour)

        hoursView.adapter = ScheduleHoursAdapter(hours)
        activitiesView.adapter = ScheduleActivitiesAdapter(activities, startHour)
    }
}

