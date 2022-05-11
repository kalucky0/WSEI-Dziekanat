package dev.kalucky0.wsei.data

import com.google.firebase.crashlytics.FirebaseCrashlytics
import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.data.web.AnnouncementsData
import dev.kalucky0.wsei.data.web.PaymentsData
import dev.kalucky0.wsei.data.web.ScheduleData
import dev.kalucky0.wsei.data.web.StudentData

class SynchronizeData(callback: (s: Boolean) -> Unit) {
    init {
        callback(tryGetStudent() && tryGetSchedule() && tryGetAnnouncements() && tryGetPayments() && tryGetGrades())
    }

    private fun tryGetStudent(): Boolean {
        try {
            val student = StudentData.get()
            Utils.db!!.studentDao().insertAll(student)
            return true
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
        }
        return false
    }

    private fun tryGetSchedule(): Boolean {
        try {
            val schedule = ScheduleData.get()
            Utils.db!!.scheduleDao().deleteAll()

            for (item in schedule)
                Utils.db!!.scheduleDao().insertAll(item)
            return true
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
        }
        return false
    }

    private fun tryGetPayments(): Boolean {
        try {
            val payments = PaymentsData.get()
            for (item in payments)
                Utils.db!!.paymentDao().insertAll(item)
            return true
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
        }
        return false
    }

    private fun tryGetAnnouncements(): Boolean {
        try {
            val announcements = AnnouncementsData.get()
            for (item in announcements)
                Utils.db!!.announcementDao().insertAll(item)
            return true
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
        }
        return false
    }

    private fun tryGetGrades(): Boolean {
        return true
    }
}
