package com.timeriver.languagesample.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.timeriver.languagesample.ui.fragment.CoroutinesFragment
import com.timeriver.languagesample.ui.fragment.LanguageFragment

class MainPageAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            CoroutinesFragment()
        } else {
            LanguageFragment()
        }
    }

}
