package dev.kalucky0.wsei.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.kalucky0.wsei.R

class ProfileFragment : Fragment() {

    private val tabs = arrayOf(R.string.personal, R.string.addresses, R.string.education)
    private lateinit var profilePagerAdapter: ProfilePagerAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profilePagerAdapter = ProfilePagerAdapter(this)
        viewPager = view.findViewById(R.id.profile)
        viewPager.adapter = profilePagerAdapter
        val tabLayout: TabLayout = view.findViewById(R.id.profile_tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(tabs[position])
        }.attach()
    }
}