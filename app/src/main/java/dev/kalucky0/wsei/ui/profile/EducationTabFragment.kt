package dev.kalucky0.wsei.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.kalucky0.wsei.Utils
import dev.kalucky0.wsei.databinding.FragmentEducationTabBinding

class EducationTabFragment :
    Fragment() {

    private lateinit var binding: FragmentEducationTabBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEducationTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val s = Utils.student

        binding.educationMatura.text = s.maturaId.ifEmpty { "—" }
        binding.educationIssueDate.text = s.maturaDate.ifEmpty { "—" }
        binding.educationGraduation.text = s.graduationHighSchool.toString()
        binding.educationSchool.text = s.highSchool.ifEmpty { "—" }
        binding.educationMaturaType.text = s.maturaType.ifEmpty { "—" }

        binding.educationDiplomaNumber.text = s.diplomaNumber.ifEmpty { "—" }
        binding.educationDateOfIssue.text = s.diplomaIssueDate.ifEmpty { "—" }
        binding.educationDateOfGraduation.text = s.graduationUni.ifEmpty { "—" }
        binding.educationUniversity.text = s.university.ifEmpty { "—" }
        binding.educationDegree.text = s.faculty.ifEmpty { "—" }
        binding.educationSpecialization.text = s.specialization.ifEmpty { "—" }
        binding.educationOtherUniversities.text = s.otherUniversity.ifEmpty { "—" }

        binding.educationWorkExperience.text = s.workExperience.ifEmpty { "—" }
    }
}