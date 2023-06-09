package com.example.postit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class RegisterFragment : Fragment() {

    lateinit var username : String
    lateinit var email : String
    lateinit var number : String
    lateinit var password : String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view : View = inflater.inflate(R.layout.fragment_register, container, false)

        val signInButton = view.findViewById<Button>(R.id.register_signInButton)

        signInButton.setOnClickListener {
            username = view.findViewById<EditText>(R.id.register_username).text.toString()
            email = view.findViewById<EditText>(R.id.register_email).text.toString()
            number = view.findViewById<EditText>(R.id.register_number).text.toString()
            password = view.findViewById<EditText>(R.id.register_password).text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && number.isNotEmpty() && password.isNotEmpty()){
                Toast.makeText(activity, "Registered", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(activity, "All fields must be filled", Toast.LENGTH_SHORT).show()
            }
        }


        return view
    }


}