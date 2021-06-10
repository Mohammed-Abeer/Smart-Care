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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerSignUp extends AppCompatActivity implements View.OnClickListener {
    private EditText editName, editEmail, editPassword,editHouseAddress,editphoneno;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_sign_up);
        mAuth = FirebaseAuth.getInstance();
        Button registerCustomer = (Button) findViewById(R.id.registerCustomer);
        registerCustomer.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   registerCustomer();
                                               }
                                           }
        );

        editName = (EditText) findViewById(R.id.name);
        editEmail = (EditText)findViewById(R.id.email);
        editHouseAddress = (EditText)findViewById(R.id.address);
        editphoneno = (EditText)findViewById(R.id.phone);
        editPassword = (EditText)findViewById(R.id.password);

        progressBar = (ProgressBar)findViewById(R.id.progress);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
           /*
            case R.id.registerCustomer:
                registerCustomer();
                break;

            */
        }

    }

    private void registerCustomer() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String name = editName.getText().toString().trim();
        String address = editHouseAddress.getText().toString().trim();
        String phone = editphoneno.getText().toString().trim();

        if (name.isEmpty()){
            editName.setError("Name is required");
            editName.requestFocus();
            return;
        }
        if (address.isEmpty()){
            editHouseAddress.setError("Address is required");
            editHouseAddress.requestFocus();
            return;
        }
        if (email.isEmpty()){
            editEmail.setError("Email is required");
            editEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please provide valid email");
            editEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            editPassword.setError("Password is required");
            editPassword.requestFocus();
            return;
        }
        if (phone.isEmpty()){
            editphoneno.setError("Phone Number is required");
            editphoneno.requestFocus();
            return;
        }
        if (password.length()<6) {
            editPassword.setError("Minimum password length is 6");
            editPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Customer customer = new Customer(name,email,address,phone);
                            FirebaseDatabase.getInstance().getReference("Customers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(CustomerSignUp.this, "Customer has been registered successfully",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(CustomerSignUp.this,Dashboard.class));
                                    }else {
                                        Toast.makeText(CustomerSignUp.this,"Failed to register. Try again!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(CustomerSignUp.this,"Failed to register. Try again!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });



    }
}
