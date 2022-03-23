package dev.kalucky0.wsei.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.databinding.FragmentPersonalTabBinding

class PersonalTabFragment :
    Fragment() {

    private lateinit var binding: FragmentPersonalTabBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val s = Utils.student

        binding.personalFullName.text = "${s.name} ${s.surname}"
        binding.personalIndex.text = s.index
        binding.personalField.text = s.course

        binding.personalName.text = s.name
        binding.personalSurname.text = s.surname
        binding.personalSecName.text = s.secondName.ifEmpty { "—" }
        binding.personalPesel.text = s.pesel.ifEmpty { "—" }
        binding.personalBirthDate.text = s.birthdate.toString()
        binding.personalBirthPlace.text = s.birtPlace
        binding.personalNip.text = s.nip.ifEmpty { "—" }
        binding.personalSex.text = s.sex
        binding.personalMaritalStatus.text = s.martialStatus
        binding.personalNationality.text = s.nationality
        binding.personalCitizenship.text = s.citizenship
        binding.personalId.text = s.id.ifEmpty { "—" }
        binding.personalIssuedBy.text = s.idIssuedBy.ifEmpty { "—" }
        binding.personalPassport.text = s.passport.ifEmpty { "—" }
        binding.personalMilitaryId.text = s.militaryId.ifEmpty { "—" }
        binding.personalMotherName.text = s.motherName.ifEmpty { "—" }
        binding.personalMotherSurname.text = s.motherSurname.ifEmpty { "—" }
        binding.personalFatherName.text = s.fatherName.ifEmpty { "—" }
        binding.personalFatherSurname.text = s.fatherSurname.ifEmpty { "—" }
        binding.personalAccountNumber.text = s.accountNumber.ifEmpty { "—" }
        binding.personalBank.text = s.bankName.ifEmpty { "—" }
    }
}