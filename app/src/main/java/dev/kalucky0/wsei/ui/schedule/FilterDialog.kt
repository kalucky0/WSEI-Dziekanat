package dev.kalucky0.wsei.ui.schedule

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import dev.kalucky0.wsei.R
import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.databinding.DialogScheduleFilterBinding

class FilterDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val b: DialogScheduleFilterBinding =
                DialogScheduleFilterBinding.inflate(it.layoutInflater)
            val builder = AlertDialog.Builder(it)

            b.checkboxLectures.isChecked = Utils.filterTypes.contains("Wyk")
            b.checkboxSeminars.isChecked = Utils.filterTypes.contains("Konw")
            b.checkboxExercises.isChecked = Utils.filterTypes.contains("Cw")
            b.checkboxLabs.isChecked = Utils.filterTypes.contains("Lab")

            builder.setTitle(getString(R.string.show))
                .setView(b.root)
                .setPositiveButton(
                    android.R.string.ok
                ) { _, _ ->
                    Utils.setFilters(
                        b.checkboxLectures.isChecked,
                        b.checkboxSeminars.isChecked,
                        b.checkboxExercises.isChecked,
                        b.checkboxLabs.isChecked,
                        it
                    )
                    it.supportFragmentManager.beginTransaction().replace(
                        R.id.nav_host_fragment,
                        ScheduleFragment()
                    ).commit()
                }
                .setNegativeButton(
                    android.R.string.cancel
                ) { _, _ -> dismiss() }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}