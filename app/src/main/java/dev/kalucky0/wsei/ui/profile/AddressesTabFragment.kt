package dev.kalucky0.wsei.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.databinding.FragmentAddressesTabBinding

class AddressesTabFragment :
    Fragment() {

    private lateinit var binding: FragmentAddressesTabBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressesTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val s = Utils.student

        binding.addressAStreet.text = s.street
        binding.addressAPostCode.text = s.postCode
        binding.addressACity.text = s.town
        binding.addressAPost.text = s.post

        binding.addressBStreet.text = s.mailStreet
        binding.addressBPostCode.text = s.mailPostCode
        binding.addressBCity.text = s.mailTown
        binding.addressBPost.text = s.mailPost

        binding.addressPrivateEmail.text = s.personalEmail
        binding.addressEmail.text = s.email
        binding.addressLandlinePhoneNumber.text = s.landline
        binding.addressMobilePhoneNumber.text = s.mobile

        binding.addressPreviousResidence.text = s.additional
    }
}