package com.example.android_studio_tut1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateAnnouncementActivity extends AppCompatActivity
{

    TextInputEditText editTextAnnouncementText, editTextAnnouncementHeaderText;
    Button buttonSend, buttonGoBack;
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_announcement);

        editTextAnnouncementText = findViewById(R.id.announcement_text);
        editTextAnnouncementHeaderText = findViewById(R.id.title);
        buttonSend = findViewById(R.id.btn_send);
        buttonGoBack = findViewById(R.id.btn_back);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();

        CollectionReference announcements = db.collection("announcements");


        buttonSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if(user == null){
                    Toast.makeText(CreateAnnouncementActivity.this, "Session expired", Toast.LENGTH_SHORT).show();
                    return;
                }

                String announcement_text = editTextAnnouncementText.getText().toString();
                String announcement_header_text = editTextAnnouncementHeaderText.getText().toString();

                if(TextUtils.isEmpty(announcement_header_text)){
                    Toast.makeText(CreateAnnouncementActivity.this, "Please enter title", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(announcement_text)){
                    Toast.makeText(CreateAnnouncementActivity.this, "Please enter announcement text", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> announcementData = new HashMap<>();
                announcementData.put("author", user.getEmail());
                announcementData.put("announcement_title", announcement_header_text);
                announcementData.put("announcement_text", announcement_text);
                announcementData.put("date", FieldValue.serverTimestamp());

                announcements.document()
                        .set(announcementData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(CreateAnnouncementActivity.this, "Success", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CreateAnnouncementActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });



            }
        });

        buttonGoBack.setOnClickListener(new View.OnClickListener()
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