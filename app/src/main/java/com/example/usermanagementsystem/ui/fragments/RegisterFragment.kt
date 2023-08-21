package com.example.usermanagementsystem.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.usermanagementsystem.R
import com.example.usermanagementsystem.data.model.UserData
import com.example.usermanagementsystem.databinding.FragmentRegisterBinding
import com.example.usermanagementsystem.ui.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment() {

    // view binding
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    // view model
    private val viewModel: MainViewModel by activityViewModels()

    private var radioBtnString = "Male"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            onClickListener()

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
                    val userData = UserData(registerName.text.toString(), email.text.toString(), password2.text.toString(), radioBtnString, date.text.toString())
                    viewModel.addUserData(userData)
                    viewModel.getUserData(userData.email).observe(viewLifecycleOwner) {
                        if (it != null) {
                            val bundle = bundleOf()
                            bundle.putString("userEmail", userData.email)
                            findNavController().navigate(R.id.action_registerFragment_to_homeFragment,bundle)
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
            } else if (password.text.toString() != password2.text.toString()){
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