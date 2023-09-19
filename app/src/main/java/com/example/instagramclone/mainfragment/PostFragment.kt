package com.example.instagramclone.mainfragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramclone.activity.PostActivity
import com.example.instagramclone.activity.RealsActivity
import com.example.instagramclone.databinding.FragmentPostBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PostFragment : BottomSheetDialogFragment() {

    private val binding by lazy {
        FragmentPostBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.createPostBt.setOnClickListener {

            activity?.startActivity(Intent(requireContext(), PostActivity::class.java))

//            val bottomSheetFragment = MyBottomSheetDialogFragment()
//            bottomSheetFragment.changeLayout(R.layout.fragment_bottom_sheet_dialog) // Pass the ID of the new layout
//            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        binding.createRellBt.setOnClickListener {

            activity?.startActivity(Intent(requireContext(), RealsActivity::class.java))

//            val bottomSheetFragment = MyBottomSheetDialogFragment()
//            bottomSheetFragment.changeLayout(R.layout.bottomsheetdialogforrell) // Pass the ID of the new layout
//            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        return binding.root
    }

}