package com.example.postit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.FirebaseDatabase

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {


        val view : View = inflater.inflate(R.layout.fragment_login, container, false)

        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val registerButton = view.findViewById<Button>(R.id.registerButton)

        loginButton.setOnClickListener {
            val username = view.findViewById<EditText>(R.id.username).text.toString()
            val password = view.findViewById<EditText>(R.id.password).text.toString()

            if ((username.isNotEmpty()) && (password.isNotEmpty())){
                val dbRef = FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users")

                dbRef.get().addOnCompleteListener{
                    if (it.isSuccessful) {
                        val result = it.result.children.mapNotNull { doc ->
                            doc.getValue(UserClass::class.java)
                        }
                        for (user in result){
                            if ((user.userName == username) && (user.userPassword == password)){
                                Toast.makeText(activity, "Logged", Toast.LENGTH_SHORT).show()
                                activity?.supportFragmentManager?.beginTransaction()?.apply{
                                    replace(R.id.fragment_container, HomeFragment())
                                    addToBackStack(null)
                                    commit()
                                }
                            }
                        }
                    }
                }
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