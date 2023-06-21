package com.example.postit

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.postit.databinding.ActivityDateBinding
import com.google.firebase.database.*

class DateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDateBinding
    private lateinit var dateList: ArrayList<DateClass>
    private lateinit var adapter: DateAdapter
    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle : Bundle? = this.intent.extras
        val username : String? = bundle?.getString("username")

        val gridLayoutManager = GridLayoutManager(this@DateActivity, 1)
        binding.recyclerViewDate.layoutManager = gridLayoutManager

        val builder = AlertDialog.Builder(this@DateActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        dateList = ArrayList()
        adapter = DateAdapter(this@DateActivity, dateList)
        binding.recyclerViewDate.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Dates").child(username.toString())
        dialog.show()

        eventListener = databaseReference!!.addValueEventListener(object: ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                dateList.clear()
                for (itemSnapshot in snapshot.children){
                    val dateClass = itemSnapshot.getValue(DateClass::class.java)
                    if (dateClass != null){
                        dateList.add(dateClass)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }

        })


        binding.fabDates.setOnClickListener {
            if (username != "") {
                val intent = Intent(this, UploadDateActivity::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Must login", Toast.LENGTH_SHORT).show()
            }
        }
    }


}