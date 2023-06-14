package com.example.postit

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.postit.databinding.ActivityDateBinding
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

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
        databaseReference = FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Dates")
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
            val intent = Intent(this, UploadDateActivity::class.java)
            startActivity(intent)
        }
    }


}