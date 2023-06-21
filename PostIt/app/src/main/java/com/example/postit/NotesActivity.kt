package com.example.postit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.postit.databinding.ActivityNotesBinding
import com.google.firebase.database.*

class NotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotesBinding
    private lateinit var noteList: ArrayList<NoteClass>
    private lateinit var adapter: NoteAdapter
    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle : Bundle? = this.intent.extras
        val username : String? = bundle?.getString("username")

        val gridLayoutManager = GridLayoutManager(this@NotesActivity, 1)
        binding.recyclerViewNotes.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(this@NotesActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        noteList = ArrayList()
        adapter = NoteAdapter(this@NotesActivity, noteList)
        binding.recyclerViewNotes.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Notes").child(username.toString())
        dialog.show()

        eventListener = databaseReference!!.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                noteList.clear()
                for (itemSnapshot in snapshot.children){
                    val noteClass = itemSnapshot.getValue(NoteClass::class.java)
                    if (noteClass != null){
                        noteList.add(noteClass)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        })

        binding.fabNotes.setOnClickListener {
            if (username != "") {
                val intent = Intent(this, UploadNoteActivity::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
            } else{
                Toast.makeText(this, "Must login", Toast.LENGTH_SHORT).show()
            }
        }
    }
}