package dev.kalucky0.wsei

import dev.kalucky0.wsei.data.web.PaymentsData
import org.junit.Test

class PaymentsDataTest {
    @Test
    fun payments_isCorrect() {
        Utils.sessionId = "your_session_id"
        assert(Utils.sessionId != "your_session_id") { "Session id is not set" }

        val payments = PaymentsData.get()
        assert(payments.size > 0) { "payments is empty" }

        for (payment in payments) {
            assert(payment.name.isNotEmpty()) { "payment name is empty" }
            assert(payment.additionalInfo.isNotEmpty()) { "payment additionalInfo is empty" }
            assert(payment.amount >= 0) { "payment amount is negative" }
            assert(payment.amountNow >= 0) { "payment amountNow is negative" }
            assert(payment.state == "Uregulowane" || payment.state == "Do uregulowania" || payment.state == "Bieżąca zaległość") { "payment state is incorrect" }
            println(payment)
        }
    }
}