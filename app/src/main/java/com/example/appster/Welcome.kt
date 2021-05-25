package com.example.appster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth


class Welcome : AppCompatActivity() {

    companion object {
        private final val RC_SIGN_IN = 123;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            // already signed in
        } else {
            startActivityForResult(
                // Get an instance of AuthUI based on the default app
                AuthUI.getInstance().createSignInIntentBuilder().build(),
                RC_SIGN_IN);
        }


    }
}