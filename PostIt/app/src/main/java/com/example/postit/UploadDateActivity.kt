package com.example.postit

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.postit.databinding.ActivityUploadBinding
import com.example.postit.databinding.ActivityUploadDateBinding
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class UploadDateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadDateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUploadDateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.uploadDateImage.setOnClickListener {
            val calendarBox = Calendar.getInstance()
            val dateBox = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                calendarBox.set(Calendar.YEAR, year)
                calendarBox.set(Calendar.MONTH, month)
                calendarBox.set(Calendar.DAY_OF_MONTH, day)
                updateText(calendarBox)
            }
            DatePickerDialog(
                this, dateBox, calendarBox.get(Calendar.YEAR), calendarBox.get(Calendar.MONTH),
                calendarBox.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.saveButton.setOnClickListener {
            saveData()
        }

    }

    private fun updateText(calendar: Calendar) {
        val dateFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(dateFormat, Locale.UK)
        binding.textDate.setText(sdf.format(calendar.time))
    }

    private fun saveData(){
        val dateTitle = binding.dateTitle.text.toString()
        val date = binding.textDate.text.toString()

        val dateClass = DateClass(dateTitle, date)

        val builder = AlertDialog.Builder(this@UploadDateActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Dates").child(dateTitle)
            .setValue(dateClass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@UploadDateActivity, "Saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(
                    this@UploadDateActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
            }

    }

}