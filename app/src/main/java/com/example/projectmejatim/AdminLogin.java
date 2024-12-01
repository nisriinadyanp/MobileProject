package com.example.projectmejatim;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLogin extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    Button btLogin;
    EditText etEmail;
    EditText etPassword;
    TextView tvAllert;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        btLogin = findViewById(R.id.btLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvAllert = findViewById((R.id.tvAllert));
        firebaseAuth = FirebaseAuth.getInstance();

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email = etEmail.getText().toString();
                String Password = etPassword.getText().toString();
                login(Email,Password);
            }
        });


    }

    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    SharedPreferences sharedPreferences = getSharedPreferences("user_pref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("role", "admin");
                    editor.apply();

                    Intent i = new Intent(getApplicationContext(), HalamanAdmin.class);
                    startActivity(i);
                    finish();
                } else {
                    tvAllert.setText("Maaf, email atau password salah. \nSilahkan coba lagi.");
                }
            }
        });
    }
}