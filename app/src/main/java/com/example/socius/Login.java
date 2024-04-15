package com.example.socius;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText mEmailEt, mPassword;

    Button mAcLogin;

    //Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;

    //progress dialog for login


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //initialising variables
        mEmailEt = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mAcLogin = findViewById(R.id.login_to_account);


        //click on login button
        mAcLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //types in email, password and batch
                String email = mEmailEt.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    //show error when email doesnt match
                    mEmailEt.setError("Invalid Email");
                    mEmailEt.setFocusable(true);
                } else {
                    //valid email
                    loginUser(email, password);
                }

            }

            private void loginUser( String email, String password) {


                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();



                                    //when user logged in send to profile
                                    startActivity(new Intent(Login.this, UserDashboard.class));
                                    finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Login.this, "Failed To Login", Toast.LENGTH_SHORT).show();


                                }


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}