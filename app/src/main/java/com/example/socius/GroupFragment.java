package com.example.socius;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.socius.adapters.AdapterGroups;
import com.example.socius.models.ModelGroups;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class GroupFragment extends Fragment {

    FirebaseAuth firebaseAuth;

    RecyclerView recyclerView;
    List<ModelGroups> postList;
    AdapterGroups adapterGroups;



    public GroupFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_group,container,false);

        recyclerView = view.findViewById(R.id.eventRecycleView);

        firebaseAuth = FirebaseAuth.getInstance();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(layoutManager);

        postList = new ArrayList<>();

        loadPosts();



        return view;
    }

    private void loadPosts() {
        CollectionReference documentReference = FirebaseFirestore.getInstance().collection("Users");

        documentReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                postList.clear();
                for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots.getDocuments()){
                    ModelGroups modelGroups = documentSnapshot.toObject(ModelGroups.class);

                    postList.add(modelGroups);

                    //adapter
                    adapterGroups = new AdapterGroups(getActivity(),postList);
                    //setting adapter to recycle view
                    recyclerView.setAdapter(adapterGroups);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}