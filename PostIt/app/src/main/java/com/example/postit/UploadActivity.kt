package com.example.postit

import android.content.Intent
import android.icu.text.DateFormat
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.postit.databinding.ActivityUploadBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

import java.util.*

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    var imageUrl : String? = null
    private var username : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bundle : Bundle? = this.intent.extras
        username = bundle?.getString("username")

        binding.saveButton.setOnClickListener {
            uploadData()
        }
    }

    private fun uploadData(){
        val title = binding.choresTitle.text.toString()
        val desc = binding.choresDesc.text.toString()
        val priority = binding.choresPriority.text.toString()
        val choreClass = ChoreClass(title, desc, priority, imageUrl)

        val builder = AlertDialog.Builder(this@UploadActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        if (username != null) {
            FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Chores").child(username.toString()).child(title)
                .setValue(choreClass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@UploadActivity, "Saved", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        this@UploadActivity, e.message.toString(), Toast.LENGTH_SHORT
                    ).show()
                }
        } else{
            Toast.makeText(this@UploadActivity, R.string.toast_need_login, Toast.LENGTH_SHORT).show()
        }
    }

}