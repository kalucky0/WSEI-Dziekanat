package dev.kalucky0.wsei.ui.payments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import dev.kalucky0.wsei.R
import dev.kalucky0.wsei.data.models.Payment
import dev.kalucky0.wsei.databinding.DialogFragmentPaymentBinding

class PaymentDialogFragment(val item: Payment) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding: DialogFragmentPaymentBinding = DialogFragmentPaymentBinding.inflate(
            layoutInflater
        )
        val alert = AlertDialog.Builder(requireContext())
            .setTitle(item.name.replaceFirstChar { it.uppercase() })
            .setView(binding.root)
            .setPositiveButton(getString(R.string.close)) { _, _ -> }
            .create()

        binding.due.text = item.due.toString()
        binding.state.text = item.state
        binding.amount.text = "${item.amount} PLN"
        binding.amountNow.text = "${item.amountNow} PLN"
        binding.additional.text = item.additionalInfo.replace("PLN ", "PLN\n")

        if (item.state == "Uregulowane") {
            binding.state.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_500))
            binding.amountNow.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green_500
                )
            )
        }

        if (item.state == "Bieżąca zaległość") {
            binding.state.setTextColor(ContextCompat.getColor(requireContext(), R.color.red_500))
            binding.amountNow.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red_500
                )
            )
        }

        return alert
    }
}