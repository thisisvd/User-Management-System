package com.example.usermanagementsystem.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.usermanagementsystem.R
import com.example.usermanagementsystem.data.model.UserData
import com.example.usermanagementsystem.databinding.FragmentUpdateBinding
import com.example.usermanagementsystem.ui.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar

class UpdateFragment : Fragment() {

    // view binding
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    // view model
    private val viewModel: MainViewModel by activityViewModels()

    private var radioBtnString = "Male"

    private lateinit var userData: UserData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            val email = arguments?.getString("userEmail")
            if (!email.isNullOrBlank()) {
                viewModel.getUserData(email).observe(viewLifecycleOwner) { data ->
                    if (data != null) {
                        userData = data
                        setDataInUI(userData)
                    } else {
                        Snackbar.make(root, "Some error occurred!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Snackbar.make(root, "Some error occurred!", Toast.LENGTH_SHORT).show()
            }

            onClickListener()
        }
    }

    private fun setDataInUI(userData: UserData) {
        binding.apply {

            registerName.setText(userData.name)
            email.setText(userData.email)
            password.setText(userData.password)
            password2.setText(userData.password)
            date.setText(userData.dob)

            if (userData.gender == "Male") {
                radioGroup.check(R.id.radio_male)
            } else {
                radioGroup.check(R.id.radio_female)
            }
        }
    }

    private fun onClickListener() {
        binding.apply {

            // radio listener
            radioGroup.setOnCheckedChangeListener { radioGroup, i ->
                radioBtnString = if (R.id.radio_male == i) {
                    "Male"
                } else {
                    "Female"
                }
            }

            // submit btn
            submitBtn.setOnClickListener {
                if (isValidData()) {
                    val userData = UserData(
                        registerName.text.toString(),
                        email.text.toString(),
                        password2.text.toString(),
                        radioBtnString,
                        date.text.toString()
                    )
                    viewModel.addUserData(userData)
                    viewModel.getUserData(userData.email).observe(viewLifecycleOwner) {
                        if (it != null) {
                            val bundle = bundleOf()
                            bundle.putString("userEmail", userData.email)
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }

    private fun isValidData(): Boolean {
        binding.apply {

            if (registerName.text.isNullOrBlank() || password.text.isNullOrBlank() || password2.text.isNullOrBlank() || date.text.isNullOrBlank()) {
                Snackbar.make(root, "Please enter all fields!", Snackbar.LENGTH_SHORT).show()
                return false
            } else if (password.text.toString() != password2.text.toString()) {
                Snackbar.make(root, "Password doesn't match!", Snackbar.LENGTH_SHORT).show()
                return false
            }

            return true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}