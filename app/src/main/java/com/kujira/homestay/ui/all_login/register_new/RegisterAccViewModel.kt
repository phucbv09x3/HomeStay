package com.kujira.homestay.ui.all_login.register_new

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
import kotlin.collections.HashMap

class RegisterAccViewModel : BaseViewModel() {
    val emailRegister = ObservableField<String>()
    val passwordRegister = ObservableField<String>()
    val userNameRegister = ObservableField<String>()
    val numberPhoneRegister = ObservableField<String>()
    val listenerShowToast = MutableLiveData<Int>()
    var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    fun click(view: View) {
        when (view.id) {
            R.id.btn_register_host_new -> {
                registerAcc(1)
            }
            R.id.btn_register_client_new -> {
                registerAcc(2)
            }

        }
    }

    private var fireBaseAuth = FirebaseAuth.getInstance()
    private var dataReference =
        FirebaseDatabase.getInstance().getReference(Constants.CLIENT).child(Constants.ACCOUNT)
    private var listEmail = mutableListOf<String>()

    private fun registerAcc(permission: Int) {
        val userName = userNameRegister.get()
        val mail = emailRegister.get()
        val passWord = passwordRegister.get()
        val phone = numberPhoneRegister.get()
        if (userName?.isEmpty() == false && mail?.isEmpty() == false
            && passWord?.isEmpty() == false && phone?.isEmpty() == false
        ) {
            val isCheckMail = listEmail.contains(mail)
            if (isCheckMail) {
                listenerShowToast.value = R.string.email_exist
            } else {
                if(passWord.length < 6) {
                    listenerShowToast.value = R.string.length_pass
                }else if(!mail.matches(Regex(emailPattern))){
                    listenerShowToast.value = R.string.email_pattern
                }else if(userName.length < 3){
                    listenerShowToast.value = R.string.length_name
                } else{
                    showLoading.onNext(true)
                    fireBaseAuth.createUserWithEmailAndPassword(mail, hashFunc(passWord))
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                showLoading.onNext(false)
                                val user = fireBaseAuth.currentUser
                                val mail = user?.email
                                val userUID = user?.uid
                                val hashMap = HashMap<String, String>()
                                hashMap[Constants.USER_NAME] = userName
                                hashMap[Constants.MAIL] = mail!!
                                hashMap[Constants.UID] = userUID!!
                                hashMap[Constants.PHONE] = phone
                                hashMap[Constants.PERMISSION] = permission.toString()
                                dataReference.child(userUID).setValue(hashMap)

                                user.sendEmailVerification()
                                    .addOnCompleteListener {
                                        listenerShowToast.value = R.string.email_verify
                                    }
                            } else {
                                listenerShowToast.value = R.string.register_fail
                            }
                        }
                }

            }
        } else {
            listenerShowToast.value = R.string.error_isEmpty
        }
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

//     private fun enCrypt(textEncrypt: String, secret: String): String {
//        val secretKeySpec = SecretKeySpec(secret.toByteArray().copyOf(16), "AES")
//        val cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING")
//        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
//        val byteArray = cipher.doFinal(textEncrypt.toByteArray())
//        return (Base64.getEncoder().encodeToString(byteArray))
//    }
    fun getListAcc(): MutableList<String> {

        val dataRef =
            FirebaseDatabase.getInstance().getReference(Constants.CLIENT).child(Constants.ACCOUNT)
        dataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listEmail.clear()
                for (snap in snapshot.children) {

                    listEmail.add(snap.child(Constants.MAIL).value.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        return listEmail

    }
}