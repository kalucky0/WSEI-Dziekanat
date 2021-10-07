package dev.kalucky0.wsei.data

import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.data.web.PaymentsData
import dev.kalucky0.wsei.data.web.ScheduleData
import dev.kalucky0.wsei.data.web.StudentData

class SynchronizeData(callback: () -> Unit) {
    init {
        Thread {
            StudentData { student ->
                Utils.db!!.studentDao().insertAll(student)
                ScheduleData { schedule ->
                    for (item in schedule)
                        Utils.db!!.scheduleDao().insertAll(item)
                    PaymentsData { payments ->
                        for (item in payments)
                            Utils.db!!.paymentDao().insertAll(item)
                        callback()
                    }
                }
            }
        }.start()
    }
}