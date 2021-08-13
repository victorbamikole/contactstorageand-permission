package com.example.storageandpermissiontask6.editcontacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.storageandpermissiontask6.R
import com.example.storageandpermissiontask6.model.Contact
import com.example.storageandpermissiontask6.model.Contacts
import com.example.storageandpermissiontask6.ui.CONTACT
import com.example.storageandpermissiontask6.ui.ContactDetails
import com.example.storageandpermissiontask6.ui.ContactsActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit_contact.*
import kotlinx.android.synthetic.main.contacts_view.*

class EditContactActivity : AppCompatActivity() {

      private lateinit var user: Contacts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        //Declare different variables and types
        user = intent.getParcelableExtra(CONTACT)!!
        val fName: EditText = findViewById(R.id.firstNameEdit)
        val lName: EditText = findViewById(R.id.lastNameEdit)
        val number: EditText = findViewById(R.id.numberEdit)
        val back: ImageView = findViewById(R.id.backEdit)
        val save: Button = findViewById(R.id.submit2)


        val bundle: Bundle? = intent.extras
        val phoneNumber = bundle?.getString("phoneNumber")
        val contact = bundle?.getString("firstName")
        val contact2 = bundle?.getString("lastName")


        fName.setText(contact)
        lName.setText(contact2)
        number.setText(phoneNumber)

        back.setOnClickListener {
            onBackPressed()
        }

        save.setOnClickListener {
            var newFirstName = fName.text
            var newLastName = lName.text
            var newNumber = number.text
            editContact(newFirstName.toString(), newLastName.toString(), newNumber.toString())
            val intent = Intent(this, ContactsActivity::class.java)
            startActivity(intent)
        }
    }

    //This function edits the saved contacts
    private fun editContact(editedFirstName: String, editedLastName: String, EditPhoneNumber: String){
        val phoneContact = mapOf<String, String>(
            "firstName" to editedFirstName,
            "lastName" to editedLastName,
            "phoneNumber" to EditPhoneNumber
        )
        val dataBase = FirebaseDatabase.getInstance().getReference("contact")
        dataBase.child(user.id!!).updateChildren(phoneContact).addOnSuccessListener {
            Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
        }
    }

}