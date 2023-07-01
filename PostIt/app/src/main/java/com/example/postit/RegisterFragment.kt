package com.example.postit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase


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
                val userClass = UserClass(username, email, number, password)

                FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users").child(username)
                    .setValue(userClass).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(activity, R.string.toast_registered, Toast.LENGTH_SHORT).show()
                            activity?.supportFragmentManager?.beginTransaction()?.apply{
                                replace(R.id.fragment_container, HomeFragment())
                                addToBackStack(null)
                                commit()
                            }
                        }
                    }.addOnFailureListener { e ->
                        Toast.makeText(activity, e.message.toString(), Toast.LENGTH_SHORT).show()
                    }



            } else{
                Toast.makeText(activity, R.string.toast_fields_filled, Toast.LENGTH_SHORT).show()
            }
        }


        return view
    }


}