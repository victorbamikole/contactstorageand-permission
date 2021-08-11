package com.example.storageandpermissiontask6.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.storageandpermissiontask6.R
import com.example.storageandpermissiontask6.databinding.ActivityAddContactBinding
import com.example.storageandpermissiontask6.model.Contact
import com.example.storageandpermissiontask6.model.Contacts
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_contact_details.*

class AddContact : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        FirebaseApp.initializeApp(this)
        setContentView(binding.root)

        var button: Button = findViewById(R.id.submit)

//        next.setOnClickListener {
//            val intent = Intent(this, ReadData::class.java)
//            startActivity(intent)
//        }
        binding.submit.setOnClickListener {

            //get values from our edit text field
            val firstName = binding.firstNameInput.text.toString()
            val lastName = binding.lastNameInput.text.toString()
            val phoneNumber = binding.numberInput.text.toString()



            database = FirebaseDatabase.getInstance().getReference("contact")
            var db = FirebaseDatabase.getInstance().getReference("contact")

            val user = Contacts(firstName = firstName, lastName = lastName, phoneNumber = phoneNumber)
            user.id = db.push().key
            database.child(user.id!!).setValue(user).addOnSuccessListener {

                binding.firstNameInput.text.clear()
                binding.lastNameInput.text.clear()
                binding.numberInput.text.clear()

                Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, ContactsActivity::class.java)
//                intent.putExtra("KeyID", db)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
