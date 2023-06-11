package com.example.postit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.postit.databinding.ActivityChoresBinding
import com.example.postit.databinding.ActivityMainBinding
import com.google.firebase.database.*

class ChoresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChoresBinding
    private lateinit var choreList: ArrayList<ChoreClass>
    private lateinit var adapter: ChoreAdapter
    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChoresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gridLayoutManager = GridLayoutManager(this@ChoresActivity, 1)
        binding.recyclerViewChores.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(this@ChoresActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        choreList = ArrayList()
        adapter = ChoreAdapter(this@ChoresActivity, choreList)
        binding.recyclerViewChores.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Chores")
        dialog.show()

        eventListener = databaseReference!!.addValueEventListener(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                choreList.clear()
                for (itemSnapshot in snapshot.children){
                    val choreClass = itemSnapshot.getValue(ChoreClass::class.java)
                    if (choreClass != null){
                        choreList.add(choreClass)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        })

        binding.fab.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }
    }
}