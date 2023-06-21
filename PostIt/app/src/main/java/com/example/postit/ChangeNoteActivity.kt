package com.example.postit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.postit.databinding.ActivityChangeNoteBinding
import com.google.firebase.database.FirebaseDatabase

class ChangeNoteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChangeNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChangeNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val title: String = intent.getStringExtra("title").toString()
        val username: String = intent.getStringExtra("username").toString()
        val dbRef = FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Notes").child(username)

        dbRef.get().addOnCompleteListener{
            if (it.isSuccessful) {
                val result = it.result.children.mapNotNull { doc ->
                    doc.getValue(NoteClass::class.java)
                }
                for (note in result){
                    if ((note.noteTitle == title)){
                        binding.actualNoteContent.text = note.noteContent
                    }
                }
            }
        }


        binding.saveChangeNoteButton.setOnClickListener {
            Toast.makeText(this, "Changes saved", Toast.LENGTH_SHORT).show()
            val content : String? = binding.noteContentChange.text.toString()
            val noteClass = NoteClass(title, content)
            FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Notes").child(username).child(title)
                .setValue(noteClass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@ChangeNoteActivity, "Saved", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(
                        this@ChangeNoteActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }
}