package com.example.postit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view : View = inflater.inflate(R.layout.fragment_login, container, false)

        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val registerButton = view.findViewById<Button>(R.id.registerButton)

        val username = view.findViewById<EditText>(R.id.username).text.toString()
        val password = view.findViewById<EditText>(R.id.password).text.toString()

        loginButton.setOnClickListener {
            if (username.isNotEmpty() && password.isNotEmpty()){
                val dbRef = FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users")


            }
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