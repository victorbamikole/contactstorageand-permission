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

        //Declare different variables and types
        var button: Button = findViewById(R.id.submit)
        binding.submit.setOnClickListener {

            //get values from our edit text field
            val firstName = binding.firstNameInput.text.toString()
            val lastName = binding.lastNameInput.text.toString()
            val phoneNumber = binding.numberInput.text.toString()


            //Initialize the FireBaseDatabase, save our text input and generate unique id for our data
            database = FirebaseDatabase.getInstance().getReference("contact")
            var db = FirebaseDatabase.getInstance().getReference("contact")

            val user =
                Contacts(firstName = firstName, lastName = lastName, phoneNumber = phoneNumber)
            user.id = db.push().key
            database.child(user.id!!).setValue(user).addOnSuccessListener {

                binding.firstNameInput.text.clear()
                binding.lastNameInput.text.clear()
                binding.numberInput.text.clear()

                Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, ContactsActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
