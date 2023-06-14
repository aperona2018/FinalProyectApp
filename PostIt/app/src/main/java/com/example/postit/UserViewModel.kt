package com.example.postit

import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {

    var user : UserClass? = null

    fun addUser(user : UserClass){
        this.user = user
    }


}