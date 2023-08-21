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
import com.example.usermanagementsystem.databinding.FragmentLoginBinding
import com.example.usermanagementsystem.ui.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    // view binding
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    // view model
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            // on click listeners
            setupOnClickListeners()

        }
    }

    private fun setupOnClickListeners() {
        binding.apply {

            // login
            loginBtn.setOnClickListener {
                validateAndLogin()
            }

            // go to register
            goToRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }

        }
    }

    // validate data and login
    private fun validateAndLogin() {
        binding.apply {

            if (!loginEmail.text.isNullOrBlank() && !loginPassword.text.isNullOrBlank()) {
                viewModel.getUserData(loginEmail.text.toString())
                    .observe(viewLifecycleOwner) { userData ->
                        if (userData != null) {
                            if (userData.email == loginEmail.text.toString() && userData.password == loginPassword.text.toString()) {
                                val bundle = bundleOf()
                                bundle.putString("userEmail", userData.email)
                                findNavController().navigate(R.id.action_loginFragment_to_homeFragment,bundle)
                            } else {
                                Snackbar.make(root, "Wrong Password", Snackbar.LENGTH_SHORT)
                                    .show()
                            }
                        } else {
                            Snackbar.make(
                                root,
                                "Email not registered or wrong email!",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Snackbar.make(root, "Please enter all fields!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}