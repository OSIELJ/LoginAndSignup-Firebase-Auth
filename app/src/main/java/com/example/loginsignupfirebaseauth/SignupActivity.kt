package com.example.loginsignupfirebaseauth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.loginsignupfirebaseauth.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSignup.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirmPassword = binding.edtConfirmPassword.text.toString()

            when {
                email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() -> {
                    Toast.makeText(this, "Campos não podem estar vazios", Toast.LENGTH_SHORT).show()
                }
                password != confirmPassword -> {
                    Toast.makeText(this, "Senhas diferentes", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, LoginActivity::class.java))

                        } else {
                            Toast.makeText(this, task.exception?.message ?: "Erro desconhecido", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        binding.tvToGoLogin.setOnClickListener {
            val intentLogin = Intent(this, LoginActivity::class.java)
            startActivity(intentLogin)

        }

    }
}