package com.example.postit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.database.FirebaseDatabase

class DateAdapter(private val context: android.content.Context, private var dateList: List<DateClass>): RecyclerView.Adapter<DateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_date_item, parent, false)
        return DateViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.recDateTitle.text = dateList[position].dateTitle
        holder.recDateText.text = dateList[position].dateText
        val creator : String = dateList[position].creator.toString()
        holder.recDelete.setOnClickListener {
            val title : String? = dateList[position].dateTitle
            if (title != null) {
                FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Dates").child(creator)
                    .child(title).removeValue().addOnSuccessListener {
                        Toast.makeText(context, "Date deleted", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

}

class DateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var recDateTitle: TextView
    var recDateText: TextView
    var recCard: CardView
    var recDelete : ShapeableImageView

    init{
        recDateTitle = itemView.findViewById(R.id.recTitle)
        recDateText = itemView.findViewById(R.id.recDate)
        recCard = itemView.findViewById(R.id.recCard)
        recDelete = itemView.findViewById(R.id.recDelete)
    }
}