package com.example.storageandpermissiontask6.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.storageandpermissiontask6.model.Contacts
import com.example.storageandpermissiontask6.ui.ContactsActivity
import com.example.storageandpermissiontask6.R

class MyAdapter(userList1: ContactsActivity, private val userList: ArrayList<Contacts>, var listen : OnItemClickListener): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private lateinit var contactListener: OnItemClickListener

    //Create click listener for our adapter class
    open interface OnItemClickListener {
        fun onContactItem(position: Int, next: View?)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        contactListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.contacts_view, parent, false)
        return MyViewHolder(itemView, listen)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.firstName.text = currentItem.firstName
        holder.lastName.text = currentItem.lastName

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class MyViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val listener = listener
        val firstName: TextView = itemView.findViewById(R.id.firstName)
        val lastName: TextView = itemView.findViewById(R.id.lastName)

        init {

            itemView.setOnClickListener {
                listener.onContactItem(adapterPosition, itemView)
            }

        }

    }
}
