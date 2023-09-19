package com.example.instagramclone

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2;
    }

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        when(position) {
            0 -> {
                return MyPostItemFragment()
            }
            1 -> {
                return MyRellItemFragment()
            }
            else -> {
                return MyPostItemFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "My Post"
            }
            1 -> {
                return "My Rell"
            }

        }
        return super.getPageTitle(position)
    }

}