package dev.kalucky0.wsei.ui.schedule

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.kalucky0.wsei.R
import dev.kalucky0.wsei.Utils.Companion.toPixels
import dev.kalucky0.wsei.data.models.Schedule
import java.util.*
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
    private lateinit var timeIndicator: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        hoursView = view.findViewById(R.id.hours)
        hoursView.layoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean = false
        }
        activitiesView = view.findViewById(R.id.activities)
        activitiesView.layoutManager = object : LinearLayoutManager(context) {
            override fun canScrollVertically(): Boolean = false
        }

        timeIndicator = view.findViewById(R.id.time_indicator)

        val activities: List<Schedule> = schedule.filter { it.day.toString() == day }
        setupSchedule(activities)
    }

    private fun setupSchedule(activities: List<Schedule>) {
        if (activities.isEmpty()) return

        startHour = max(floor(activities[0].timeFrom).roundToInt() - 2, 8)
        endHour = min(ceil(activities.last().timeTo).roundToInt() + 2, 24)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val minute = Calendar.getInstance().get(Calendar.MINUTE)
            val today = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            val date = day.split("-")[2].toInt()

            val currentHour = hour + minute / 60f

            if (currentHour > startHour && currentHour < endHour && today == date) {
                timeIndicator.alpha = 1f
                timeIndicator.translationY =
                    toPixels(79f, context) * (currentHour - startHour) + toPixels(9f, context)
            }
        }

        val hours: ArrayList<String> = ArrayList()
        for (i in startHour..endHour) hours.add("$i:00")

        hoursView.layoutParams.height = toPixels(80f, context) * (endHour - startHour)
        activitiesView.layoutParams.height = toPixels(80f, context) * (endHour - startHour)

        hoursView.adapter = ScheduleHoursAdapter(hours)
        activitiesView.adapter = ScheduleActivitiesAdapter(activities, startHour)
    }
}

