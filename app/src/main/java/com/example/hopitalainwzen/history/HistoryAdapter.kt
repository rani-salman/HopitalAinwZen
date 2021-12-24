package com.example.tictactoe.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hopitalainwzen.User
import com.example.hopitalainwzen.R

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObject

class HistoryAdapter(query: Query,private val listener: (User) -> Unit
) : FirestoreAdapter<HistoryAdapter.ViewHolder>(query) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.name)
        private val domain: TextView = itemView.findViewById(R.id.doctor_domain)
        private val sickness : TextView = itemView.findViewById(R.id.sickness)
        private val phone: TextView = itemView.findViewById(R.id.phone)
        private val notes: TextView = itemView.findViewById(R.id.notes)

        fun bind(snapshot: DocumentSnapshot) {

            val user = snapshot.toObject<User>()
            val typoo =user?.type
         if(user?.type=="doctor") {
                name.text = "Doctor's Name: " + user?.doctorProperties?.doctorname
                domain.text = "Domain: " + user?.doctorProperties?.domain
                sickness.visibility = View.GONE
                phone.visibility =  View.GONE
                notes.visibility = View.GONE
            } else {
                name.text =" Patients Name: " + user?.patientProperties?.name
              domain.visibility= View.GONE
                phone.text =" Patients Phone: " + user?.patientProperties?.phone
                sickness.text =" Sickness: " + user?.patientProperties?.sickness
                notes.text =" notes" +
                        ": " + user?.patientProperties?.notes

           }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item =getSnapshot(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            item.toObject<User>()?.let {
                listener(it)
            }

        }
    }
}