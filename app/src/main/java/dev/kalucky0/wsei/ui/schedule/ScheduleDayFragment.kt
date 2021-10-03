package dev.kalucky0.wsei.ui.schedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.kalucky0.wsei.data.models.Activity
import dev.kalucky0.wsei.R
import dev.kalucky0.wsei.Utils
import kotlin.math.*

class ScheduleDayFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_schedule_day, container, false)
    }

    private var startHour: Int = 8
    private var endHour: Int = 24

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey("day") }?.apply {
            val day = requireArguments()["day"] as Int;

            Log.e("Day", day.toString())
            Log.e("Utils.schedule.size", Utils.schedule.size.toString())

            val activities: ArrayList<Activity> =
                if (day >= Utils.schedule.size) ArrayList() else Utils.schedule[day]

            if (day < Utils.schedule.size) {
                startHour = max(floor(activities[0].timeFrom).roundToInt() - 2, 8)
                endHour = min(ceil(activities.last().timeTo).roundToInt() + 2, 24)
            }

            val hours: ArrayList<String> = ArrayList()
            for (i in startHour..endHour) hours.add("$i:00")

            val hoursView: RecyclerView = view.findViewById(R.id.hours)
            hoursView.layoutManager = LinearLayoutManager(context)
            hoursView.layoutParams.height = Utils.toPixels(80f, context) * (endHour - startHour)
            hoursView.layoutManager = object : LinearLayoutManager(context) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

            val hoursAdapter = ScheduleHoursAdapter(hours)
            hoursView.adapter = hoursAdapter

            val activitiesView: RecyclerView = view.findViewById(R.id.activities)
            activitiesView.layoutManager = LinearLayoutManager(context)
            activitiesView.layoutParams.height =
                Utils.toPixels(80f, context) * (endHour - startHour)
            activitiesView.layoutManager = object : LinearLayoutManager(context) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

            val activitiesAdapter = ScheduleActivitiesAdapter(activities, startHour)
            activitiesView.adapter = activitiesAdapter
        }
    }
}

