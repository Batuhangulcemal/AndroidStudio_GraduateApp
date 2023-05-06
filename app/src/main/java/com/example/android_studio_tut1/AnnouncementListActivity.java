package com.example.android_studio_tut1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class AnnouncementListActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;

    LinearLayout linearLayout;
    CollectionReference announcements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_list);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();
        linearLayout = findViewById(R.id.linear_layout);
        announcements = db.collection("announcements");

        CreateAnnouncements();


    }

    private void CreateAnnouncements(){

        Query query = announcements;

        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Announcement announcement = document.toObject(Announcement.class);

                                Button button = new Button(getApplicationContext());
                                button.setText(announcement.announcement_title);
                                button.setLayoutParams(new
                                        LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT));

                                button.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        Toast.makeText(AnnouncementListActivity.this, announcement.announcement_title, Toast.LENGTH_SHORT).show();

                                    }
                                });

                                linearLayout.addView(button);

                            }
                        } else {
                            Toast.makeText(AnnouncementListActivity.this, "Failed to fetch announcements", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }
}