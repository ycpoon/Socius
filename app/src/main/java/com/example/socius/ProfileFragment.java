package com.example.socius;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;




import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.google.firebase.storage.FirebaseStorage.getInstance;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private static final String FIRE_LOG = "Fire_Log";
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseFirestore firebaseDatabase;
    StorageReference storageReference;
    DocumentReference documentReference;
    //path for image


    //view from xml file

    TextView Name, Email, Batch;
    CardView Logout;
    ProgressDialog pd;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        //initiate firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseFirestore.getInstance();
        storageReference = getInstance().getReference();


        //init views
        Name = view.findViewById(R.id.user_name);
        Email = view.findViewById(R.id.user_email);

        Logout = view.findViewById(R.id.logout);

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                startActivity(new Intent(ProfileFragment.this.getActivity(), MainActivity.class));

            }
        });

        //initialise progress dialog
        pd = new ProgressDialog(getActivity());


        String uid = user.getUid();


        //finding info based on user email
        firebaseDatabase.collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    String email = documentSnapshot.getString("Email");

                    String name = documentSnapshot.getString("Name");


                    Name.setText(name);

                    Email.setText(email);


                } else {
                    Log.d(FIRE_LOG, "Error");
                }
            }
        });
       return view;
    }

}



