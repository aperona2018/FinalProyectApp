package com.example.postit

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.database.FirebaseDatabase


class NoteAdapter(private val context: android.content.Context, private var noteList: List<NoteClass>): RecyclerView.Adapter<NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.recNoteTitle.text = noteList[position].noteTitle
        val username = noteList[position].noteCreator

        holder.recDelete.setOnClickListener {
            val title : String? = noteList[position].noteTitle
            if (title != null) {
                FirebaseDatabase.getInstance("https://postit-48c08-default-rtdb.europe-west1.firebasedatabase.app").getReference("Notes").child(username.toString())
                    .child(title).removeValue().addOnSuccessListener {
                        Toast.makeText(context, R.string.toast_note_deleted, Toast.LENGTH_SHORT).show()
                    }
            }
        }

        holder.recChange.setOnClickListener {
            val changeIntent = Intent(this.context, ChangeNoteActivity::class.java)
            changeIntent.putExtra("title", holder.recNoteTitle.text)
            changeIntent.putExtra("username", username)
            this.context?.startActivity(changeIntent)
        }
    }

}

class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var recNoteTitle: TextView
    var recCard: CardView
    var recDelete : ShapeableImageView
    var recChange : ShapeableImageView

    init{
        recNoteTitle = itemView.findViewById(R.id.recNoteTitle)
        recCard = itemView.findViewById(R.id.recCard)
        recDelete = itemView.findViewById(R.id.recDelete)
        recChange = itemView.findViewById(R.id.recUpdateImage)
    }
}