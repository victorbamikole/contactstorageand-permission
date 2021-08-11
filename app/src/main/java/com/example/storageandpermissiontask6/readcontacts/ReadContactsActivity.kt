package com.example.storageandpermissiontask6.readcontacts

import android.Manifest.permission.READ_CONTACTS
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.SimpleCursorAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.example.storageandpermissiontask6.R
import com.example.storageandpermissiontask6.ui.MY_PERMISSIONS_REQUEST_READ_CONTACTS
import kotlinx.android.synthetic.main.activity_contacts.*
import kotlinx.android.synthetic.main.activity_read_contacts.*
import java.util.jar.Manifest
import kotlin.contracts.contract

const val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1

class ReadContactsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_contacts)

        readContacts()
        var requestButton = findViewById<Button>(R.id.request)

        requestButton.setOnClickListener{
            readContacts()

        }

    }
    private fun readContacts(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED){
            read()
        }else{
            askPermission()
        }
    }

    private fun askPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, android.Manifest.permission.READ_CONTACTS
            )
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.READ_CONTACTS),
                MY_PERMISSIONS_REQUEST_READ_CONTACTS
            )
        }else{

            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_CONTACTS),
                MY_PERMISSIONS_REQUEST_READ_CONTACTS)
        }
    }

    private fun read(){
        var cursor: Cursor? = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        startManagingCursor(cursor)

        var from = arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID)

        var to = intArrayOf(android.R.id.text1, android.R.id.text2)

        var simple: SimpleCursorAdapter = SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor,from,to)

        listView.adapter = simple
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        var hide: TextView = findViewById(R.id.errorMsg)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            read()
            hide.visibility = View.INVISIBLE
        }else{
            hide.visibility = View.VISIBLE
//            Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show()
        }
    }

}