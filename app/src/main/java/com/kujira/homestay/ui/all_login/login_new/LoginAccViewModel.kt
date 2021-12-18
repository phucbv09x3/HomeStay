package com.kujira.homestay.ui.all_login.login_new

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kujira.homestay.R
import com.kujira.homestay.ui.base.BaseViewModel
import com.kujira.homestay.utils.Constants
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class LoginAccViewModel : BaseViewModel() {
    val emailLogin = ObservableField<String>()
    val passwordLogin = ObservableField<String>()
    val listenerClick = MutableLiveData<Int>()
    private var auth = FirebaseAuth.getInstance()
    val listener = MutableLiveData<Int>()

    companion object {
        const val LOGIN = 1
        const val FORGOT_PASSWORD = 2
        const val REGISTER_ACC = 3
        const val PERMISSION_1 = 4
        const val PERMISSION_2 = 5
    }

    fun click(view: View) {
        when (view.id) {
            R.id.btn_login_new -> {
                checkSignIn()
            }
            R.id.tv_forgot_password_new -> {
                listenerClick.value = FORGOT_PASSWORD
            }
            R.id.tv_register_new -> {
                listenerClick.value = REGISTER_ACC
            }
        }
    }

    private var listAcc = mutableListOf<Acc>()
    fun getListAcc(): MutableList<Acc> {
        val dataRef =
            FirebaseDatabase.getInstance().getReference(Constants.CLIENT).child(Constants.ACCOUNT)

        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listAcc.clear()
                for (snap in snapshot.children) {
                    val mail = snap.child(Constants.MAIL).value.toString()
                    val permission = snap.child(Constants.PERMISSION).value.toString()
                    listAcc.add(Acc(mail, permission))
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        return listAcc
    }

    private fun checkSignIn() {
        val email = emailLogin.get()?.trim() ?: ""
        val password = passwordLogin.get()?.trim() ?: ""
        // val isCheck = listAcc.contains(email)

        if (email.isNotEmpty() && password.isNotEmpty()) {
            if(listAcc.isEmpty()) {
                listener.value = R.string.repair
            }
            val acc = listAcc.firstOrNull {
                it.email.lowercase(Locale.getDefault()) == email.lowercase(Locale.getDefault())
            }
            if (acc != null) {
                showLoading.onNext(true)
                auth.signInWithEmailAndPassword(email, hashFunc(password))
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val user = auth.currentUser
                            if (user!!.isEmailVerified) {
                                showLoading.onNext(false)
                                if (acc.permission == "1") {
                                    listener.value = PERMISSION_1
                                } else {
                                    listener.value = PERMISSION_2
                                }
                            } else {
                                showLoading.onNext(false)
                                listener.value = R.string.error_auth
                            }
                        }
                    }
                    .addOnCanceledListener {
                        showLoading.onNext(false)
                        listener.value = R.string.check_info
                    }
                    .addOnFailureListener {
                        showLoading.onNext(false)
                    }
            } else {
                listener.value = R.string.not_exit_account
            }

        } else {
            listener.value = R.string.error_isEmpty
        }
    }
    private fun enCrypt(textEncrypt: String, secret: String): String {
        val secretKeySpec = SecretKeySpec(secret.toByteArray().copyOf(16), "AES")
        val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val byteArray = cipher.doFinal(textEncrypt.toByteArray())
        return (Base64.getEncoder().encodeToString(byteArray))
    }
    private fun hashFunc(textEncrypt: String): String {
        val md5 = MessageDigest.getInstance("MD5")//SHA-256
        var sbb = ""
        val byteArray: ByteArray = md5.digest(textEncrypt.toByteArray(StandardCharsets.UTF_8))
        for (item in byteArray) {
            sbb += String.format("%02x", item)
        }
        return sbb
    }

}

data class Acc(
    var email: String,
    var permission: String
)