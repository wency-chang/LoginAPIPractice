package com.wency.login.login

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.fragment.findNavController
import com.wency.login.MyApplication
import com.wency.login.NavHostDirections
import com.wency.login.databinding.LoginFragmentBinding
import com.wency.login.repository.ServiceLocator

class LoginFragment: Fragment() {
    lateinit var binding: LoginFragmentBinding
    private val viewModel by viewModels<LoginViewModel>
    { ViewModelFactory(MyApplication.instance.repository)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = LoginFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.loginSuccess.observe(viewLifecycleOwner, Observer { loginInfo ->
            if (loginInfo != null){
                Toast.makeText(this.requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                findNavController().navigate(NavHostDirections.globalActionToTimezoneFragment(loginInfo))
                viewModel.clearLiveData()
            }
        })

        viewModel.loginFailed.observe(viewLifecycleOwner, Observer {
            if (it != null){
                Toast.makeText(this.requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.clearLiveData()
            }
        })

        val mailTextBox = binding.editTextEmailAddressLayout
        val mailFormatErrorText = binding.wrongFormatText
        viewModel.mailFormat.observe(viewLifecycleOwner, Observer {
            if (it || viewModel.mailTextInput.value.isNullOrEmpty()){
                mailTextBox.isErrorEnabled = false
                mailTextBox.error = null
                mailFormatErrorText.visibility = View.GONE
            } else {
                mailTextBox.isErrorEnabled = true
                mailTextBox.error = "wrong format"
                mailFormatErrorText.visibility = View.VISIBLE
            }
        })

        viewModel.mailTextInput.observe(viewLifecycleOwner, Observer {
            viewModel.clickEditText(true)
        })

        viewModel.passwordTextInput.observe(viewLifecycleOwner, Observer {
            viewModel.clickEditText(false)
        })

        binding.button.setOnClickListener {
            viewModel.login()
        }

        return binding.root
    }
}