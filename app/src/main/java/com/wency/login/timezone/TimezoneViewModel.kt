package com.wency.login.timezone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wency.login.data.LoginResponse
import com.wency.login.data.TimezoneUpdate
import com.wency.login.data.UpdateStatus
import com.wency.login.repository.Repository
import kotlinx.coroutines.launch

class TimezoneViewModel(private val repository: Repository, val userInfo: LoginResponse) : ViewModel() {

    private val _updateStatus = MutableLiveData(UpdateStatus.Done)
    val updateStatus : LiveData<UpdateStatus>
        get() = _updateStatus

    private val _updateFailedInfo = MutableLiveData<String?>(null)
    val updateFailedInfo : LiveData<String?>
        get() = _updateFailedInfo

    fun getTimezoneArray(): Array<String>{
        val list = mutableListOf<String>()
        for (t in 0..24){
            val timezone = getPositionToGMTTime(t)
            if (timezone < 0){
                list.add("GMT - ${timezone*-1}")
            } else {
                list.add("GMT + $timezone")
            }
        }
        return list.toTypedArray()
    }

    private fun getPositionToGMTTime(index: Int): Int{
        return index - 12
    }

    fun getGMTTimeToPosition(time: Int): Int{
        return time + 12
    }

    fun updateTimezone(position: Int){
        if (getPositionToGMTTime(position) != userInfo.timezone) {
            _updateStatus.value = UpdateStatus.Updating
            viewModelScope.launch {
                val result = repository.updateUser(userInfo, TimezoneUpdate(getPositionToGMTTime(position)))
                if (result.error == null) {
                    updateSuccess(getPositionToGMTTime(position))
                } else {
                    updateFailed(result.error!!)
                }
            }
        }
    }

    private fun updateSuccess(newTimezone: Int){
        userInfo.timezone = newTimezone
        _updateStatus.value = UpdateStatus.UpdateSuccess
    }

    private fun updateFailed(e: String){
        _updateFailedInfo.value = e
    }

    fun updateDone(){
        _updateStatus.value = UpdateStatus.Done
    }

}