package com.example.smartcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ManagerSignUp extends AppCompatActivity implements View.OnClickListener {
    private EditText editManName, editManEmail, editManPassword,editCode,editPhoneNo;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_sign_up);
        mAuth = FirebaseAuth.getInstance();
        editManEmail = findViewById(R.id.email_man);
        editManName = findViewById(R.id.name_man);
        editPhoneNo = findViewById(R.id.phone_man);
        editCode = findViewById(R.id.code);
        progressBar = (ProgressBar)findViewById(R.id.progress);
        editManPassword = findViewById(R.id.password_man);
        Button registerManager = findViewById(R.id.registerManager);
        registerManager.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerManager:
                registerManager();
                break;

    }
}

    private void registerManager() {
        String email = editManEmail.getText().toString().trim();
        String password = editManPassword.getText().toString().trim();
        String name = editManName.getText().toString().trim();
        String code = editCode.getText().toString().trim();
        String phone = editPhoneNo.getText().toString().trim();

        if (name.isEmpty()){
            editManName.setError("Name is required");
            editManName.requestFocus();
            return;
        }

        if (email.isEmpty()){
            editManEmail.setError("Email is required");
            editManEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editManEmail.setError("Please provide valid email");
            editManEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            editManPassword.setError("Password is required");
            editManPassword.requestFocus();
            return;
        }
        if(!code.equals("trump")){
            editCode.setError("Not Correct Code");
            editCode.requestFocus();
            return;
        }
        if (phone.isEmpty()){
            editPhoneNo.setError("Phone Number is required");
            editPhoneNo.requestFocus();
            return;
        }
        if (password.length()<6) {
            editManPassword.setError("Minimum password length is 6");
            editManPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Manager manager = new Manager(name,email,phone);
                            FirebaseDatabase.getInstance().getReference("Managers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(manager).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ManagerSignUp.this,"Manager is registered successfully",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(ManagerSignUp.this,Dashboard.class));
                                    }else {
                                        Toast.makeText(ManagerSignUp.this,"Failed to register. Try again!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(ManagerSignUp.this,"Failed to register. Try again!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
    }
