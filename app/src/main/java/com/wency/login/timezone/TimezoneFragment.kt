package com.wency.login.timezone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.wency.login.MyApplication
import com.wency.login.R
import com.wency.login.data.UpdateStatus
import com.wency.login.databinding.TimezoneFragmentBinding
import com.wency.login.repository.ServiceLocator

class TimezoneFragment: Fragment() {
    lateinit var binding: TimezoneFragmentBinding
    private val viewModel by viewModels<TimezoneViewModel> { TimezoneViewModelFactory(
            MyApplication.instance.repository,
            TimezoneFragmentArgs.fromBundle(requireArguments()).loginInfo)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TimezoneFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val dropDownAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner_layout,
            viewModel.getTimezoneArray()
        )
        binding.autoCompleteTextView.apply {
            setAdapter(dropDownAdapter)
            setText(
                dropDownAdapter.getItem(viewModel.getGMTTimeToPosition(viewModel.userInfo.timezone)),
                false
            )
            setOnItemClickListener { _, _, position, _ ->
                viewModel.updateTimezone(position)
            }
        }

        viewModel.updateFailedInfo.observe(viewLifecycleOwner, Observer {
            if (it != null){
                Toast.makeText(this.requireContext(),
                    "Update failed : $it", Toast.LENGTH_SHORT).show()
                viewModel.updateDone()
            }
        })

        viewModel.updateStatus.observe(viewLifecycleOwner, Observer {
            if (it == UpdateStatus.UpdateSuccess){
                Toast.makeText(this.requireContext(),
                    "Update Success", Toast.LENGTH_SHORT).show()
                viewModel.updateDone()
            }
        })

        return binding.root
    }
}