package com.example.socius;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;





import java.util.HashMap;

public class Registration extends AppCompatActivity {

    EditText mEmailET, mPasswordET, mName, mConPass;
    Button mRegisterBtn;


    //progress dialog when user registers
    ProgressDialog progressDialog;
    //Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);



        //initialise registration request

        mPasswordET = findViewById(R.id.password);

        mRegisterBtn = findViewById(R.id.sign);

        mName = findViewById(R.id.reg_name);
        mConPass = findViewById(R.id.reg_confirm_pass);
        mEmailET = findViewById(R.id.email);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);


        progressDialog.setMessage("Registering Your Account...");


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //input email,password and batch
                String email = mEmailET.getText().toString().trim();
                String password = mPasswordET.getText().toString().trim();

                String confirm = mConPass.getText().toString().trim();

                String name = mName.getText().toString().trim();

                //validating inputs
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    //highlights error in the email
                    mEmailET.setError("Invalid Email!");
                    mEmailET.setFocusable(true);

                }
                else if  (password.length()<6){
                    //show error for the password
                    mPasswordET.setError("Password Too Short!");
                    mPasswordET.setFocusable(true);
                }
                else if(!password.equals(confirm)){
                    mConPass.setError("Passwords Do Not Match");
                    mConPass.setFocusable(true);
                }



                else if(name.equals("")) {
                    mName.setError("Fill In Your Name");
                    mName.setFocusable(true);
                }

                else if(name.length()>15) {
                    mName.setError("Use A Shorter Nickname");
                    mName.setFocusable(true);
                }

                else{
                    //register user
                    registerUser(email , password);
                }


            }
        });




    }

    private void registerUser(String email, String password) {
        progressDialog.show();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, continue with registration
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();

                            //get user uid and email
                            String email = user.getEmail();
                            String uid = user.getUid();


                            //when user is registered, data is stored in database
                            HashMap<Object,String> hashMap = new HashMap<>();
                            //append info into hash map
                            hashMap.put("Email",email);
                            hashMap.put("UID",uid);
                            hashMap.put("TotalTime", "0");
                            hashMap.put("DailyHour", "0");
                            hashMap.put("DailyMinute","0");

                            hashMap.put("Name",mName.getText().toString().trim());


                            //Firebase instance
                            FirebaseFirestore database = FirebaseFirestore.getInstance();
                            //path to store to store the users
                            database.collection("Users").document(uid).set(hashMap).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Registration.this, "Failed To Register", Toast.LENGTH_SHORT).show();
                                }
                            });


                            Toast.makeText(Registration.this, "Successfully Registered! \n"+user.getEmail(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Registration.this, UserDashboard.class));


                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(Registration.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Registration.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



}
}
