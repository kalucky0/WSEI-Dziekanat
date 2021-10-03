package dev.kalucky0.wsei

import android.content.Context
import dev.kalucky0.wsei.data.models.Activity
import dev.kalucky0.wsei.data.models.Student

class Utils {
    companion object {
        var sessionId: String = ""
        lateinit var student: Student
        lateinit var schedule: ArrayList<ArrayList<Activity>>

        fun toPixels(dp: Float, context: Context?): Int {
            val scale: Float = context!!.resources.displayMetrics.density
            return (dp * scale + 0.5f).toInt()
        }
    }
}