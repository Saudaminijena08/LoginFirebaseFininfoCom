package com.example.loginfirebasefininfocom

import UserListAdapter
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.loginfirebasefininfocom.Constants.Companion.UserArrayList
import com.example.loginfirebasefininfocom.databinding.ActivityLoginBinding
import com.example.loginfirebasefininfocom.databinding.LoaderLayoutBinding
import com.example.loginfirebasefininfocom.model.User
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity(), TextWatcher {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var loaderFragment: LoaderLayoutBinding
    private val dialog: Dialog by lazy { Dialog(this) }
    private lateinit var logoutTimer: CountDownTimer
    private val usernameRegexPattern = "^.{10}$".toRegex()
    private val passwordRegexPattern ="^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%^&*(),.?\":{}|<>])(?=\\S+\$).{8,}\$".toRegex()


    val db: FirebaseFirestore = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.password.addTextChangedListener(this)



        binding.login.setOnClickListener {
            validation()
        }
    }

    private fun validation() {
        val userName = binding.username.text.toString().trim()
        val password = binding.password.text.toString()

        if (userName.isEmpty()) {
            binding.username.error = "Please enter UserName"
            binding.username.requestFocus()
        } else if (userName.length<10) {
            binding.username.error = "UserName must be 10 character"
            binding.username.requestFocus()
        } else if (password.isEmpty()) {
            binding.password.error = "Please enter password"
        }else if(password.length<7){
            binding.password.error = "Password must be 7 character"
        } else if (!passwordRegexPattern.matches(password)) {
            binding.password.error =
                "Please use a strong password"
        } else {
            showLoader()
            UserArrayList.clear()
            db.collection("UserInfo").get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    hideloader()
                    for (document in task.result) {
                        if (document.id.equals(packageName, ignoreCase = true)) {
                            val userInfoMap = document.data
                            val userDataList =
                                userInfoMap["UserData"] as ArrayList<HashMap<String, Any>>
                            Log.e("LoginTag", "getUserDataList: $userDataList")
                            for (i in 0 until userDataList.size) {
                                val userMap = userDataList[i]
                                val user = User()
                                user.name = userMap["Name"].toString()
                                user.age = userMap["Age"].toString()
                                user.city = userMap["Address"].toString()
                                UserArrayList.add(user)
                            }
                        }
                    }

                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)

                }

            }

        }
    }

    fun showLoader() {
        loaderFragment = LoaderLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(loaderFragment.root)
        Glide.with(this).load(R.drawable.loadergif).into(loaderFragment.ivLoader)
        val wlmp: WindowManager.LayoutParams = dialog.window!!.attributes
        dialog.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
        wlmp.gravity = Gravity.CENTER_HORIZONTAL
        dialog.window!!.attributes = wlmp
        dialog.setTitle(null)
        dialog.setCancelable(false)
        dialog.setOnCancelListener(null)
        dialog.show()
    }

    fun hideloader() {
        dialog.dismiss()
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (!passwordRegexPattern.matches(p0.toString())) {
            binding.password.error =
                "Please use a strong password"
        }else{
            binding.password.error =null
        }
    }

    override fun afterTextChanged(p0: Editable?) {
    }
}