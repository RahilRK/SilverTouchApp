package com.rk.silvertouchapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rk.silvertouchapp.R
import com.rk.silvertouchapp.databinding.FragmentAddContactBinding
import com.rk.silvertouchapp.databinding.FragmentContactListBinding

class ContactListFragment : Fragment() {

    var TAG = this.javaClass.simpleName

    lateinit var binding : FragmentContactListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContactListBinding.inflate(layoutInflater,container,false)

        return binding.root
    }
}