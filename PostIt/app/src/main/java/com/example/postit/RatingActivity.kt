package com.example.postit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.postit.databinding.ActivityRatingBinding
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDateTime

class RatingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRatingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRatingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle : Bundle? = this.intent.extras
        val username : String? = bundle?.getString("username")

        binding.ratingButton.setOnClickListener {
            if (username != "") {
                if (binding.ratingBar != null) {
                    val rating = binding.ratingBar.rating

                    val builder = AlertDialog.Builder(this@RatingActivity)
                    builder.setCancelable(false)
                    builder.setView(R.layout.progress_layout)
                    val dialog = builder.create()
                    dialog.show()
                    val datetime = LocalDateTime.now()
                    var dateStr = datetime.toString()
                    dateStr.replace(".".toRegex(), "")
                    print(dateStr)

                    FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app")
                        .getReference("Rating").child(username.toString())
                        .setValue(rating).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this@RatingActivity, "Saved", Toast.LENGTH_SHORT)
                                    .show()
                                finish()
                            }
                        }.addOnFailureListener { e ->
                            Toast.makeText(
                                this@RatingActivity, e.message.toString(), Toast.LENGTH_SHORT
                            ).show()
                        }

                } else {
                    Toast.makeText(this, "Rating bar must be filled", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Must login", Toast.LENGTH_SHORT).show()
            }
        }
    }
}