package com.wency.login.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wency.login.data.LoginResponse
import com.wency.login.data.UpdateStatus
import com.wency.login.data.UserInfo
import com.wency.login.repository.Repository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {

    private val _mailFormat = MutableLiveData<Boolean>()
    val mailFormat: LiveData<Boolean>
        get() = _mailFormat

    private val _completeStatus = MutableLiveData<Boolean>(false)
    val completeStatue: LiveData<Boolean>
        get() = _completeStatus

    val mailTextInput = MutableLiveData<String>("")
    val passwordTextInput = MutableLiveData<String>("")

    private val _loginSuccess = MutableLiveData<LoginResponse?>(null)
    val loginSuccess: LiveData<LoginResponse?>
        get() = _loginSuccess

    private val _loginFailed = MutableLiveData<String?>(null)
    val loginFailed: LiveData<String?>
        get() = _loginFailed

    private val _updateStatus = MutableLiveData<UpdateStatus>(UpdateStatus.Done)
    val updateStatus : LiveData<UpdateStatus>
        get() = _updateStatus

    private fun checkEmail() {
        mailTextInput.value?.let { mail ->
            _mailFormat.value = mail.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(mail).matches()
        }
    }

    private fun checkInfoCompleted() {
        passwordTextInput.value?.let { password ->
            _completeStatus.value = password.isNotEmpty() && mailFormat.value == true
        }
    }

    fun clickEditText(mail: Boolean) {
        if (mail) {
            checkEmail()
        }
        checkInfoCompleted()
    }

    fun login() {
        mailTextInput.value?.let { mail ->
            passwordTextInput.value?.let { password ->
                _updateStatus.value = UpdateStatus.Updating
                viewModelScope.launch {
                    val result = repository.userLogin(UserInfo(mail, password))
                    if (result.error == null) {
                        loginSuccess(result)
                    } else {
                        loginFailed(result.error!!)
                    }
                    _updateStatus.value = UpdateStatus.Done
                }
            }
        }
    }

    private fun loginSuccess(loginResponse: LoginResponse) {
        _loginSuccess.value = loginResponse
    }

    private fun loginFailed(error: String) {
        _loginFailed.value = error
    }

    fun clearLiveData(){
        _loginSuccess.value = null
        _loginFailed.value = null
    }
}
