package dev.kalucky0.wsei.ui.schedule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.kalucky0.wsei.R
import dev.kalucky0.wsei.Utils

class ScheduleActivitiesAdapter(private val data: ArrayList<Activity>) :
    RecyclerView.Adapter<ScheduleActivitiesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val subject: TextView = view.findViewById(R.id.subject)
        val time: TextView = view.findViewById(R.id.time)
        val info: TextView = view.findViewById(R.id.info)
        val activity: LinearLayout = view.findViewById(R.id.activity)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_schedule, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.subject.text = data[i].subject
        viewHolder.time.text = "${data[i].timeFrom + 7}:00 - ${data[i].timeTo + 7}:00"
        viewHolder.info.text = "${data[i].instructor} • ${data[i].location}"


        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.topMargin =
            (Utils.toPixels(78f, viewHolder.activity.context) * if(i == 0) data[i].timeFrom else data[i].timeFrom - data[i - 1].timeTo).toInt();
        params.height = (Utils.toPixels(
            78f,
            viewHolder.activity.context
        ) * (data[i].timeTo - data[i].timeFrom)).toInt();
        viewHolder.activity.layoutParams = params
    }

    override fun getItemCount() = data.size
}