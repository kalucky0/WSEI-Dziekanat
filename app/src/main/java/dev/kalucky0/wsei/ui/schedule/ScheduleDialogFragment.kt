package dev.kalucky0.wsei.ui.schedule

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import dev.kalucky0.wsei.R
import dev.kalucky0.wsei.data.models.Schedule
import dev.kalucky0.wsei.databinding.DialogFragmentScheduleBinding
import kotlinx.datetime.toJavaLocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.floor

class ScheduleDialogFragment(val schedule: Schedule): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: DialogFragmentScheduleBinding = DialogFragmentScheduleBinding.inflate(
            LayoutInflater.from(context))

        val alert = AlertDialog.Builder(requireContext())
            .setTitle(schedule.subject)
            .setView(binding.root)
            .setPositiveButton(getString(R.string.close)) { _, _ -> }
            .create()

        val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM")
        binding.date.text = schedule.day.toJavaLocalDate().format(formatter).replaceFirstChar { it.uppercase() }
        binding.timeFrom.text = parseHour(schedule.timeFrom)
        binding.timeTo.text = parseHour(schedule.timeTo)
        binding.instructor.text = schedule.instructor
        binding.form.text = schedule.type
        binding.group.text = schedule.group
        binding.room.text = schedule.location

        return alert
    }

    private fun parseHour(time: Float): String {
        val hour: Int = floor(time).toInt()
        val minutes: Int = (60 * (time - hour)).toInt()
        return "$hour:${minutes.toString().padStart(2, '0')}"
    }
}