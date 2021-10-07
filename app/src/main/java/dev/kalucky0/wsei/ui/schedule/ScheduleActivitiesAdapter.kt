package dev.kalucky0.wsei.ui.schedule

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import dev.kalucky0.wsei.data.models.Schedule
import dev.kalucky0.wsei.R
import dev.kalucky0.wsei.Utils
import kotlin.math.floor

class ScheduleActivitiesAdapter(private val data: List<Schedule>, private val startHour: Int) :
    RecyclerView.Adapter<ScheduleActivitiesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val subject: TextView = view.findViewById(R.id.subject)
        val item: LinearLayout = view.findViewById(R.id.item)
        val time: TextView = view.findViewById(R.id.time)
        val info: TextView = view.findViewById(R.id.info)
        val activity: LinearLayout = view.findViewById(R.id.activity)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_schedule, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val item = data[i]
        viewHolder.subject.text = item.subject
        viewHolder.time.text = "${parseHour(item.timeFrom)} - ${parseHour(item.timeTo)}"
        viewHolder.info.text = "${item.instructor} • ${item.location} • ${item.type}"

        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.topMargin =
            (Utils.toPixels(78f, viewHolder.activity.context) * if(i == 0) item.timeFrom - startHour else item.timeFrom - data[i - 1].timeTo).toInt()
        params.height = (Utils.toPixels(
            78f,
            viewHolder.activity.context
        ) * (item.timeTo - item.timeFrom)).toInt()
        viewHolder.activity.layoutParams = params

        viewHolder.item.setOnClickListener {
            val manager: FragmentManager = (viewHolder.itemView.context as AppCompatActivity).supportFragmentManager
            ScheduleDialogFragment(item).show(manager, "")
        }
    }

    private fun parseHour(time: Float): String {
        val hour: Int = floor(time).toInt()
        val minutes: Int = (60 * (time - hour)).toInt()
        return "$hour:${minutes.toString().padStart(2, '0')}"
    }

    override fun getItemCount() = data.size
}