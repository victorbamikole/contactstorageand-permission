package com.example.storageandpermissiontask6.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.storageandpermissiontask6.R
import com.example.storageandpermissiontask6.databinding.ActivityAddContactBinding
import com.example.storageandpermissiontask6.editcontacts.EditContactActivity
import com.example.storageandpermissiontask6.model.Contact
import com.example.storageandpermissiontask6.model.Contacts
import com.example.storageandpermissiontask6.readcontacts.ReadContactsActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_contact.*
import kotlinx.android.synthetic.main.activity_contact_details.*
import kotlinx.android.synthetic.main.activity_contact_details.view.*
import kotlinx.android.synthetic.main.contacts_view.*
import kotlinx.android.synthetic.main.contacts_view.view.*


var MY_PERMISSIONS_REQUEST_CALL_PHONE = 1

class ContactDetails : AppCompatActivity() {

    private lateinit var oldContact: Contacts

    private lateinit var binding: ActivityAddContactBinding

    private lateinit var database: DatabaseReference

    private lateinit var pNumber: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)
        oldContact = intent.getParcelableExtra(CONTACT)!!
        pNumber = findViewById(R.id.number)
        binding = ActivityAddContactBinding.inflate(layoutInflater)
        val fName: TextView = findViewById(R.id.contactName)
        val lName: TextView = findViewById(R.id.contactName2)
        val back: ImageView = findViewById(R.id.backButton)
        val idKey: TextView = findViewById(R.id.idKey)
        val pImage: ImageView? = findViewById(R.id.circleImageView)
        val call: ImageView = findViewById(R.id.call)
        val shareTo: ImageView = findViewById(R.id.share)
        var delete: ImageView = findViewById(R.id.deleteButton)
        val ref: DatabaseReference
        val dataRef: DatabaseReference
        val store: DatabaseReference


        val bundle: Bundle? = intent.extras

        val phoneNumber = bundle?.getString("PhoneNumber")
        val contact = bundle?.getString("ContactName")
        val contact2 = bundle?.getString("ContactName2")
        val id = bundle?.getString("Keys")

        fName.text = contact
        pNumber.text = phoneNumber
        lName.text = contact2
//        idKey.text = id


        back.setOnClickListener {
            onBackPressed()
        }

        call.setOnClickListener() {
            requestPermissionResult()
        }
        shareTo.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.ACTION_SEND, "Share ${firstName?.firstName} ${lastName?.lastName}, ${number?.number} Contact")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
        delete.setOnClickListener {
            database = FirebaseDatabase.getInstance().getReference("contact")
            var user: Contact = Contact("", "firstName", "lastName")
            if (id != null) {
                if (deleteContact(id)) {
                    val intent = Intent(this, ContactsActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(
                        this,
                        "${user.firstName} deleted successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this, "Contact was not deleted successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }
        edit.setOnClickListener {
            val intent = Intent(this, EditContactActivity::class.java)
            intent.putExtra("firstName", contact)
            intent.putExtra("lastName", contact2)
            intent.putExtra("phoneNumber", phoneNumber)
            intent.putExtra("keys", id)
            intent.putExtra(CONTACT, oldContact)
            startActivity(intent)
        }
    }

    private fun requestPermissionResult() {
        if (!hasCalPhonePermission()) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 0)
        } else {
            callPhone()
        }
    }
    private fun callPhone() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${pNumber.text}"))
        startActivity(intent)
    }
    private fun hasCalPhonePermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED

    private fun deleteContact(id: String): Boolean {
        var result = true
        var dataRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("contact")
        dataRef.child(id).removeValue().addOnSuccessListener {
            result = true
        }.addOnFailureListener {
            result = false
        }
//        val intent = Intent(this, ContactsActivity::class.java)
//        startActivity(intent)
        Toast.makeText(this, "contact deleted successfully", Toast.LENGTH_SHORT).show()
        return result
    }

    private fun editContact(id: String, firstName: String, lastName: String,  phoneNumber: String){
        val phoneContact = mapOf<String, String>(
            "id" to id,
            "firstName" to firstName,
            "lastName" to lastName,
            "phoneNumber" to phoneNumber
        )
        val dataBase = FirebaseDatabase.getInstance().getReference("contact")
        dataBase.child(id).updateChildren(phoneContact).addOnSuccessListener {
            Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 0 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            callPhone()
        } else {
            Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
        }
    }

}