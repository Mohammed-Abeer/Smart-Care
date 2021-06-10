package com.example.smartcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button customersignin = findViewById(R.id.customersignin);
        Button customersignup = findViewById(R.id.customersignup);
        Button managersignin = findViewById(R.id.managersignin);
        Button managersignup = findViewById(R.id.managersignup);


        customersignin .setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   startActivity(new Intent(MainActivity.this, CustomerSignIn.class));
                                               }
                                           }
                );
        customersignup.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  startActivity(new Intent(MainActivity.this, CustomerSignUp.class));
                                              }
                                          }
        );
        managersignin.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 startActivity(new Intent(MainActivity.this, ManagerSignIn.class));
                                             }
                                         }
        );
        managersignup.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 startActivity(new Intent(MainActivity.this, ManagerSignUp.class));
                                             }
                                         }
        );


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /*
            case R.id.customersignup:
                startActivity(new Intent(this,CustomerSignUp.class));
                break;

            case R.id.customersignin:

                startActivity(new Intent(this,CustomerSignIn.class));

            case R.id.managersignup:

                startActivity(new Intent(this,ManagerSignUp.class));
            case R.id.managersignin:
                startActivity(new Intent(this,ManagerSignIn.class));
                */
        }
    }
}
