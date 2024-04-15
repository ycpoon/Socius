package com.example.socius;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.google.firebase.storage.FirebaseStorage.getInstance;


public class TimerFragment extends Fragment {
    Chronometer chronometer;
    FloatingActionButton btStart,btStop;

    private boolean isResume;
    Handler handler;
    long tMilliSec, tStart,tBuff,tUpdate = 0L;
    int sec,min,hour;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseFirestore firebaseDatabase;

    DocumentReference documentReference;


    public TimerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec = (int) (tUpdate/1000);
            hour = sec/3600;
            min = (sec % 3600) / 60;
            sec = sec%60;

            chronometer.setText(String.format("%02d",hour)+":"+String.format("%02d",min)+":"+String.format("%02d",sec));
            handler.postDelayed(this,1000 );

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseFirestore.getInstance();


        chronometer = view.findViewById(R.id.stopwatch);
        btStart = view.findViewById(R.id.start_stopwatch);
        btStop = view.findViewById(R.id.stop_stopwatch);

        handler = new Handler();

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isResume){
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    chronometer.start();
                    isResume = true;
                    btStop.setVisibility(View.GONE);
                    btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause));


            }else{
                    tBuff += tMilliSec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    isResume = false;
                    btStop.setVisibility(View.VISIBLE);
                    btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));

                }
        }


    });
        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isResume) {
                    btStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));

                    String uid = user.getUid();

                    firebaseDatabase.collection("Users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                long TotalTime = Long.parseLong(documentSnapshot.getString("TotalTime"));

                                long newTotal = TotalTime + tMilliSec;


                                Map<String, Object> map = new HashMap<>();
                                map.put("TotalTime", Long.toString(newTotal));
                                map.put("DailyHour", Long.toString(hour));
                                map.put("DailyMinute", Long.toString(min));


                                documentReference = FirebaseFirestore.getInstance().collection("Users").document(uid);
                                documentReference.update(map);


                            }
                            tMilliSec = 0L;
                            tStart = 0L;
                            tBuff = 0L;
                            tUpdate = 0L;


                            sec = 0;
                            min = 0;
                            hour = 0;
                            chronometer.setText("00:00:00");

                        }
                    });

                }
            }
        });
        return view;
    }
}









