package dev.kalucky0.wsei.ui.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.kalucky0.wsei.R
import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.data.models.Payment

class PaymentsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val financesTable: RecyclerView = view.findViewById(R.id.financesList)
        financesTable.layoutManager = LinearLayoutManager(context)
        financesTable.addItemDecoration(
            DividerItemDecoration(
                financesTable.context,
                DividerItemDecoration.VERTICAL
            )
        )

        Thread {
            val payments: List<Payment> = Utils.db?.paymentDao()!!.getAll()
            financesTable.adapter = PaymentViewAdapter(payments)
        }.start()
    }
}