package dev.kalucky0.wsei.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.kalucky0.wsei.R
import dev.kalucky0.wsei.Utils

class ScheduleDayFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_schedule_day, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey("day") }?.apply {

            val hours: ArrayList<String> = ArrayList()
            for(i in 7..24) hours.add("$i:00")

            val hoursView: RecyclerView = view.findViewById(R.id.hours)
            hoursView.layoutManager = LinearLayoutManager(context)
            hoursView.layoutParams.height = Utils.toPixels(79f, context) * 17
            hoursView.layoutManager = object : LinearLayoutManager(context) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

            val hoursAdapter = ScheduleHoursAdapter(hours)
            hoursView.adapter = hoursAdapter

            val activities: ArrayList<Activity> = ArrayList()

            activities.add(Activity(6.5f, 8f, "Statystyka opisowa", "Jan Kowalski", "Wyk", "F Praga"));

            val activitiesView: RecyclerView = view.findViewById(R.id.activities)
            activitiesView.layoutManager = LinearLayoutManager(context)
            activitiesView.layoutParams.height = Utils.toPixels(79f, context) * 17
            activitiesView.layoutManager = object : LinearLayoutManager(context) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

            val activitiesAdapter = ScheduleActivitiesAdapter(activities)
            activitiesView.adapter = activitiesAdapter
        }
    }
}

