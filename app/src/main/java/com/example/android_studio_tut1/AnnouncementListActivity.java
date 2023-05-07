package com.example.android_studio_tut1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;


public class AnnouncementListActivity extends AppCompatActivity {

    private final int announcementDurationDay = 5;
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;

    LinearLayout linearLayout;

    Button backButton;
    CollectionReference announcements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_list);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();
        linearLayout = findViewById(R.id.linear_layout);
        backButton = findViewById(R.id.back_button);
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

                                //control if outdated announcement
                                Date now = new Date(); // gets current date
                                Date announcementDate = announcement.date;
                                long difference_In_Time
                                        = now.getTime() - announcementDate.getTime(); //milliseconds
                                long difference_In_Days
                                        = (difference_In_Time
                                        / (1000 * 60 * 60 * 24))
                                        % 365;
                                if(difference_In_Days > announcementDurationDay){
                                    //announcement is outdated
                                }else{
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
                                            //Toast.makeText(AnnouncementListActivity.this, announcement.announcement_title, Toast.LENGTH_SHORT).show();

                                            //start activity with parameter
                                            Intent intent = new Intent(getApplicationContext(), AnnouncementActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("documentId", document.getId());
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });

                                    linearLayout.addView(button);
                                }
                            }
                        } else {
                            Toast.makeText(AnnouncementListActivity.this, "Failed to fetch announcements", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }


}