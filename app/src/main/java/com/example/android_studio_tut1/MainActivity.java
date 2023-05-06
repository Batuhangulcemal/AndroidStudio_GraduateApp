package com.example.android_studio_tut1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity
{
    FirebaseFirestore db;
    FirebaseAuth auth;
    Button buttonLogout, buttonProfile, buttonCreateAnnouncement, buttonShowAnnouncements;
    TextView textView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        buttonLogout = findViewById(R.id.logout);
        buttonProfile = findViewById(R.id.profile);
        buttonCreateAnnouncement = findViewById(R.id.create_announcement);
        buttonShowAnnouncements = findViewById(R.id.show_announcements);
        textView = findViewById(R.id.user_details);
        user = auth.getCurrentUser();

        CollectionReference profiles = db.collection("profiles");

        if(user == null)
        {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            //Document name is user email
            DocumentReference docRef = profiles.document(user.getEmail());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        textView.setText("task successfull");
                        DocumentSnapshot document = task.getResult();
                        if (!document.exists()) {
                            //Profile does not exist
                            //Redirect to create profile activity
                            textView.setText("no doc");
                            Intent intent = new Intent(getApplicationContext(), CreateProfileActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }else{
                        textView.setText(task.getException().toString());
                    }
                }
            });
        }

        buttonLogout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        buttonProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();

            }
        });

        buttonCreateAnnouncement.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), CreateAnnouncementActivity.class);
                startActivity(intent);
                finish();

            }
        });

        buttonShowAnnouncements.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), AnnouncementListActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}