package com.example.usermanagementsystem.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.usermanagementsystem.R
import com.example.usermanagementsystem.data.model.UserData
import com.example.usermanagementsystem.databinding.FragmentHomeBinding
import com.example.usermanagementsystem.ui.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    // view binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // view model
    private val viewModel: MainViewModel by activityViewModels()

    // local user data
    private lateinit var userData: UserData

    // delete local variable
    private var isDeleteClicked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            // handle on back clicked
            onBack()

            // observer
            viewModelObservers()

            // onclick listeners
            onClickListeners()
        }
    }

    // listeners
    private fun onClickListeners() {
        binding.apply {

            // on update click
            updateBtn.setOnClickListener {
                val bundle = bundleOf()
                bundle.putString("userEmail", userData.email)
                findNavController().navigate(R.id.action_homeFragment_to_updateFragment, bundle)
            }

            // on delete click listener
            deleteBtn.setOnClickListener {
                if (userData != null) {
                    viewModel.deleteUserData(userData)
                    isDeleteClicked = true
                    viewModel.getUserData(userData.email).observe(viewLifecycleOwner) {
                        if (it == null) {
                            findNavController().popBackStack()
                        }
                    }
                } else {
                    Snackbar.make(root, "Some error occurred!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // view model observers
    private fun viewModelObservers() {
        binding.apply {
            viewModel.apply {

                // get users data
                val email = arguments?.getString("userEmail")
                if (!email.isNullOrBlank()) {
                    getUserData(email).observe(viewLifecycleOwner) { data ->
                        if (!isDeleteClicked) {
                            if (data != null) {
                                userData = data
                                showDataInUI(userData)
                            } else {
                                Snackbar.make(root, "Some error occurred!", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                } else {
                    Snackbar.make(root, "Some error occurred!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // init data fetched from db
    private fun showDataInUI(userData: UserData) {
        binding.apply {

            // name
            name.text = userData.name

            // email
            email.text = userData.email

            // gender
            gender.text = userData.gender

            // dob
            dob.text = userData.dob
        }
    }

    // handle onBack pressed
    private fun onBack() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // do nothing...
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}