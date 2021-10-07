package dev.kalucky0.wsei.ui.payments

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import dev.kalucky0.wsei.R
import dev.kalucky0.wsei.data.models.Payment
import dev.kalucky0.wsei.databinding.ItemPaymentsBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

class PaymentViewAdapter(
    private val values: List<Payment>
) : RecyclerView.Adapter<PaymentViewAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemPaymentsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
        val item: LinearLayout = binding.paymentItem

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemPaymentsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        holder.item.setOnClickListener {
            val manager: FragmentManager = (holder.itemView.context as AppCompatActivity).supportFragmentManager
            PaymentDialogFragment(item).show(manager, "")
        }

        holder.idView.text = item.name.replaceFirstChar { it.uppercase() }
        if(item.state == "Uregulowane")
            holder.contentView.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.green_500))
        holder.contentView.text = "${item.amountNow} PLN"
    }

    override fun getItemCount(): Int = values.size
}