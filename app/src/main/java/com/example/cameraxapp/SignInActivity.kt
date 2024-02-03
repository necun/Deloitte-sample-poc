package com.example.cameraxapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import java.security.MessageDigest

class SignInActivity : AppCompatActivity() {

    internal enum class AppFragment {
        SingleAccount,
        MultipleAccount,
        B2C
    }

    private var mCurrentFragment: AppFragment? = null
    private lateinit var btnSignIn:CardView
    private lateinit var rl1:RelativeLayout
    private lateinit var fl: FrameLayout
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        println("34345454543545"+generateBase64EncodedSHA1("78:64:56:63:58:35:DE:34:8C:DB:4A:62:5C:DE:68:BD:DB:6B:07:3E"))
        btnSignIn = findViewById(R.id.btnSignIn)
        rl1= findViewById(R.id.rlOne)
        fl= findViewById(R.id.fragment_container)

        supportActionBar?.hide()

        btnSignIn.setOnClickListener {
            setCurrentFragment(AppFragment.SingleAccount)
//            startActivity(Intent(this,MainActivity::class.java))
//            finish()
        }
    }

    private fun setCurrentFragment(newFragment: AppFragment) {
        if (newFragment == mCurrentFragment) {
            return
        }

        mCurrentFragment = newFragment
        displayFragment(mCurrentFragment as AppFragment)

    }

    private fun attachFragment(fragment: Fragment) {
        rl1.visibility = View.GONE
        fl.visibility=View.VISIBLE
        supportFragmentManager
            .beginTransaction()
            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun displayFragment(fragment: AppFragment) {
        when (fragment) {
            AppFragment.SingleAccount -> {
                attachFragment(SingleAccountModeFragment())
                return
            }

         AppFragment.MultipleAccount -> {
                attachFragment(MultipleAccountModeFragment())
                return
            }

            AppFragment.B2C -> {
                attachFragment(B2CModeFragment())
                return
            }
        }
    }



    fun generateBase64EncodedSHA1(input: String): String {
        // Convert the input string to bytes
        val bytes = input.toByteArray()

        // Compute the SHA1 hash
        val sha1Digest = MessageDigest.getInstance("SHA-1").digest(bytes)

        // Base64 encode the SHA1 hash
        return Base64.encodeToString(sha1Digest, Base64.NO_WRAP)
    }
}

