package dev.kalucky0.wsei.data

import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.data.web.PaymentsData
import dev.kalucky0.wsei.data.web.ScheduleData
import dev.kalucky0.wsei.data.web.StudentData

class SynchronizeData(callback: () -> Unit) {
    init {
        val student = StudentData.get()
        Utils.db!!.studentDao().insertAll(student)

        val schedule = ScheduleData.get()
        Utils.db!!.scheduleDao().deleteAll()

        for (item in schedule)
            Utils.db!!.scheduleDao().insertAll(item)

        val payments = PaymentsData.get()
        for (item in payments)
            Utils.db!!.paymentDao().insertAll(item)

        callback()
    }
}
