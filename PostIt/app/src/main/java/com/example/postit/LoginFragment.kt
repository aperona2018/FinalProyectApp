package com.example.postit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginFragment : Fragment() {

    lateinit var username : EditText
    lateinit var password : EditText



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view : View = inflater.inflate(R.layout.fragment_login, container, false)

        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val registerButton = view.findViewById<Button>(R.id.registerButton)

        username = view.findViewById(R.id.username)
        password = view.findViewById(R.id.password)

        loginButton.setOnClickListener {
            Toast.makeText(activity, "Login", Toast.LENGTH_SHORT).show()
        }


        registerButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply{
                replace(R.id.fragment_container, RegisterFragment())
                addToBackStack(null)
                commit()
            }
        }

        return view
    }
}