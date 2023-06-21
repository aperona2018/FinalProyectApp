package com.example.postit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.postit.databinding.ActivityUploadNoteBinding
import com.google.firebase.database.FirebaseDatabase

class UploadNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadNoteBinding
    private var username : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUploadNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle : Bundle? = this.intent.extras
        username = bundle?.getString("username")

        binding.saveNoteButton.setOnClickListener {
            saveData()
        }
    }

    private fun saveData(){
        val noteTitle = binding.noteTitle.text.toString()
        val noteContent = binding.noteContent.text.toString()


        val noteClass = NoteClass(noteTitle, noteContent)
        noteClass.noteCreator = username

        val builder = AlertDialog.Builder(this@UploadNoteActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Notes").child(username.toString()).child(noteTitle)
            .setValue(noteClass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@UploadNoteActivity, "Saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(
                    this@UploadNoteActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
            }

    }
}