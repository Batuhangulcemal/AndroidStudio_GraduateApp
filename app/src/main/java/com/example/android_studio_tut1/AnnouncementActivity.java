package com.example.android_studio_tut1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AnnouncementActivity extends AppCompatActivity
{

    FirebaseFirestore db;

    TextView titleTextView, announcementTextView;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        db = FirebaseFirestore.getInstance();
        titleTextView = findViewById(R.id.title_text);
        announcementTextView = findViewById(R.id.announcement_text);
        backButton = findViewById(R.id.back_button);

        Bundle bundle = getIntent().getExtras();
        String documentId = bundle.getString("documentId");

        //show related document(announcement)

        CollectionReference announcements = db.collection("announcements");
        DocumentReference announcementDocumentRef = announcements.document(documentId);


        announcementDocumentRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    // Document found in the offline cache
                    DocumentSnapshot document = task.getResult();

                    Announcement announcement = document.toObject(Announcement.class);

                    titleTextView.setText(announcement.announcement_title);
                    announcementTextView.setText(announcement.announcement_text);

                } else {
                    Toast.makeText(AnnouncementActivity.this, "Couldn't receive announcement data", Toast.LENGTH_SHORT).show();

                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener()
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