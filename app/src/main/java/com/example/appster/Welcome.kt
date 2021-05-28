package com.example.appster

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig.*

class Welcome : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(
                    listOf(
                        GoogleBuilder().build(),
                        EmailBuilder().build()
                    )
                )
                .build(),
            RC_SIGN_IN
        )
    }

    companion object{
        private const val RC_SIGN_IN = 123;
    }

}