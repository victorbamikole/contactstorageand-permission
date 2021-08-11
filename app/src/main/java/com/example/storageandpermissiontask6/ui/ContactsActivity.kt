package com.example.storageandpermissiontask6.ui

import android.Manifest
import android.app.ActivityOptions
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storageandpermissiontask6.R
import com.example.storageandpermissiontask6.adapter.MyAdapter
import com.example.storageandpermissiontask6.model.Contacts
import com.example.storageandpermissiontask6.readcontacts.ReadContactsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_contact.view.*
import kotlinx.android.synthetic.main.activity_contact_details.*
import kotlinx.android.synthetic.main.activity_contact_details.view.*
import kotlinx.android.synthetic.main.activity_contacts.*
import kotlinx.android.synthetic.main.contacts_view.*

const val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1

const val CONTACT = "Contact"

class ContactsActivity : AppCompatActivity(), MyAdapter.OnItemClickListener {

    private lateinit var dbref: DatabaseReference
    private lateinit var userArrayList: ArrayList<Contacts>
    private lateinit var mAdapter: MyAdapter
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var rView: RecyclerView
    private lateinit var read: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        read = findViewById(R.id.readContacts)

        userRecyclerView = findViewById(R.id.userList)
        userArrayList = ArrayList()
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        getUsersData(this)

        val button = findViewById<FloatingActionButton>(R.id.addButton)

        button.setOnClickListener {
            val intent = Intent(this, AddContact::class.java)
            startActivity(intent)
        }

        val adapter2 = MyAdapter(this, userArrayList, this)
        userList.adapter = adapter2

        userList.layoutManager = LinearLayoutManager(this)
        userList.layoutManager = LinearLayoutManager(this)

        read.setOnClickListener {
            val intent = Intent(this@ContactsActivity, ReadContactsActivity::class.java)

            val callIntent = Intent(Intent.ACTION_CALL)
            if (ActivityCompat.checkSelfPermission(
                    this@ContactsActivity,
                    Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this@ContactsActivity,
                        Manifest.permission.READ_CONTACTS
                    )
                ) {
                } else {
                    ActivityCompat.requestPermissions(
                        this@ContactsActivity, arrayOf(Manifest.permission.READ_CONTACTS),
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS

                    )
                }

            }
            startActivity(intent)
        }

    }

    private fun getUsersData(list: MyAdapter.OnItemClickListener) {
        dbref = FirebaseDatabase.getInstance().getReference("contact")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    userArrayList.clear()
                    for (userSnapshot in snapshot.children) {
                        val users = userSnapshot.getValue(Contacts::class.java)
                        userArrayList.add(users!!)
                    }
                    userRecyclerView.adapter = MyAdapter(ContactsActivity(), userArrayList, list)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    override fun onContactItem(position: Int, next: View?) {
        val getId = intent.getStringExtra("KeyID")
        Toast.makeText(this@ContactsActivity, "Selected", Toast.LENGTH_SHORT).show()
        val view = next!!.findViewById<ImageView>(R.id.pIcon)
        val intent = Intent(this@ContactsActivity, ContactDetails::class.java)
        val options = ActivityOptions.makeSceneTransitionAnimation(
            this@ContactsActivity,
            view,
            "example_transition"
        )

        intent.putExtra(CONTACT, userArrayList[position])

        intent.putExtra("PhoneNumber", userArrayList[position].phoneNumber)
        intent.putExtra("ContactName", userArrayList[position].firstName)
        intent.putExtra("ContactName2", userArrayList[position].lastName)
        intent.putExtra("Keys", userArrayList[position].id)
        startActivity(intent)
    }
}

