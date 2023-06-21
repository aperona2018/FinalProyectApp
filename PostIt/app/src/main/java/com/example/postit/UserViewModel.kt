package com.example.postit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    private var _username = MutableLiveData(String())
    val username : LiveData<String> get() = _username

    fun setUsername(username : String){
        this._username.value = username
    }

    fun getUsername(): String? {
        return this.username.value
    }
}
