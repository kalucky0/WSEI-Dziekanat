package dev.kalucky0.wsei.ui.profile

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProfilePagerAdapter(
    fm: Fragment
) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int = 3

    override fun createFragment(i: Int): Fragment {
        return when (i) {
            0 -> PersonalTabFragment()
            1 -> AddressesTabFragment()
            else -> EducationTabFragment()
        }
    }
}