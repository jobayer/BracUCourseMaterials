package com.jobayr.bcm.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.api.load
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jobayr.bcm.R
import com.jobayr.bcm.activities.MainActivity
import com.jobayr.bcm.extensions.*
import com.jobayr.bcm.extras.Const.GOOGLE_SIGN_IN_CODE
import kotlinx.android.synthetic.main.fragment_account.view.*

class AccountFragment : Fragment() {

    private lateinit var rootView: View
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDB: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_account, container, false)
        init()
        return rootView
    }

    private fun init() {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDB = Firebase.database.reference
        initUI()
        initData()
        createSignInRequest()
        initCallback()
    }

    private fun initUI() {
        if (firebaseAuth.currentUser == null) {
            rootView.userNameView.text = getString(R.string.not_available)
            rootView.userEmailView.text = getString(R.string.not_available)
        } else {
            rootView.signInBtn.makeGone()
            rootView.signOutBtn.makeVisible()
        }
    }

    private fun initData() {
        firebaseAuth.currentUser?.let {
            rootView.userNameView.text = it.displayName
            rootView.userEmailView.text = it.email
            rootView.userImageView.load(it.photoUrl)
        }
    }

    private fun initCallback() {
        rootView.signInBtn.setOnClickListener {
            signIn()
        }
        rootView.signOutBtn.setOnClickListener {
            signOut()
        }
    }

    private fun createSignInRequest() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_CODE)
    }

    private fun signOut() {
        firebaseAuth.signOut()
        showToast("Signed Out")
        requireActivity().finishAffinity()
        startActivity(Intent(requireContext(), MainActivity::class.java))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                showErrorToast("Failed To Sign In")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    showSuccessToast("Singed In Successfully!!")
                    requireActivity().finishAffinity()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                } else {
                    showErrorToast("Failed To Sign In")
                }
            }
    }

    private fun getOrCreateUser() {
       firebaseAuth.currentUser!!.uid.let {
           firebaseDB.child("users/$it")
               .addListenerForSingleValueEvent(object : ValueEventListener {
                   override fun onCancelled(error: DatabaseError) {}

                   override fun onDataChange(snapshot: DataSnapshot) {

                   }
               })
       }
    }

}
