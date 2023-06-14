package com.example.postit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

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
    }

}

class DateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var recDateTitle: TextView
    var recDateText: TextView
    var recCard: CardView

    init{
        recDateTitle = itemView.findViewById(R.id.recTitle)
        recDateText = itemView.findViewById(R.id.recDate)
        recCard = itemView.findViewById(R.id.recCard)
    }
}