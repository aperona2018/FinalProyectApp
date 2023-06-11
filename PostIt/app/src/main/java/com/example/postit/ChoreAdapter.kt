package com.example.postit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ChoreAdapter(private val context: android.content.Context, private var choreList: List<ChoreClass>): RecyclerView.Adapter<ChoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChoreViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return ChoreViewHolder(view)
    }

    override fun getItemCount(): Int {
        return choreList.size
    }

    override fun onBindViewHolder(holder: ChoreViewHolder, position: Int) {
        holder.recTitle.text = choreList[position].choreTitle
        holder.recDesc.text = choreList[position].choreDesc
        holder.recPriority.text = choreList[position].chorePriority
    }

}

class ChoreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var recTitle: TextView
    var recDesc: TextView
    var recPriority: TextView
    var recCard: CardView

    init{
        recTitle = itemView.findViewById(R.id.recTitle)
        recDesc = itemView.findViewById(R.id.recDesc)
        recPriority = itemView.findViewById(R.id.recPriority)
        recCard = itemView.findViewById(R.id.recCard)
    }
}
