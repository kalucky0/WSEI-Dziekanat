package dev.kalucky0.wsei.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class SchedulePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val days: Array<String> = arrayOf("Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota", "Niedziela");
    override fun getCount(): Int = 5

    override fun getItem(i: Int): Fragment {
        val fragment = ScheduleDayFragment()
        fragment.arguments = Bundle().apply {
            putInt("day", i)
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return days[position];
    }
}