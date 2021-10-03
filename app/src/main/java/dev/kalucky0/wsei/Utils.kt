package dev.kalucky0.wsei

import android.content.Context

class Utils {
    companion object {
        var sessionId: String = "";

        fun toPixels(dp: Float, context: Context?): Int {
            val scale: Float = context!!.resources.displayMetrics.density
            return (dp * scale + 0.5f).toInt()
        }
    }
}