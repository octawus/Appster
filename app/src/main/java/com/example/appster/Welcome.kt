package com.example.appster

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.*
import com.google.firebase.auth.FirebaseAuth

class Welcome : AppCompatActivity() {

    val AUTH_REQUEST_CODE = 123
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var listener:FirebaseAuth.AuthStateListener
    lateinit var providers: List<AuthUI.IdpConfig>

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(listener)
    }

    override fun onStop() {
        FirebaseAuth.getInstance().signOut()
        if(listener != null)
            firebaseAuth.removeAuthStateListener(listener)
        super.onStop()
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        init()
    }

    private fun init() {
        providers = arrayListOf(
            AppleBuilder().build(),
            GoogleBuilder().build(),
            EmailBuilder().build(),
            PhoneBuilder().build()
        )

        firebaseAuth = FirebaseAuth.getInstance()
        listener = FirebaseAuth.AuthStateListener { p0 ->
            val user = p0.currentUser
            if(user != null) {
                Toast.makeText(this@Welcome, ""+user.uid, Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setLogo(R.mipmap.ic_launcher)
                    .setTheme(R.style.LoginTheme)
                    .build(), AUTH_REQUEST_CODE)
            }
        }
    }

}