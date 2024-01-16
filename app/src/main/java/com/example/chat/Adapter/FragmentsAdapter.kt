package com.example.chat.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.chat.Fragments.CallsFragment
import com.example.chat.Fragments.ChatsFragment
import com.example.chat.Fragments.StatusFragment

class FragmentsAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0-> ChatsFragment()
            1-> StatusFragment()
            2-> CallsFragment()
            else-> ChatsFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title:String?=null
        title = when(position) {
            0-> "CHATS"
            1-> "STATUS"
            2-> "CALLS"
            else-> "CHATS"
        }
        return title
    }
}