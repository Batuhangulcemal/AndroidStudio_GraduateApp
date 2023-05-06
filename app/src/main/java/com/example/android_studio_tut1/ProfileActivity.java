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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileActivity extends AppCompatActivity
{
    FirebaseFirestore db;
    FirebaseAuth auth;
    Button buttonMainMenu;
    FirebaseUser user;

    TextView textName, textLastname, textSchoolRegistrationYear, textGraduationYear, textSchoolName, textDegree, textWorkingCountry, textWorkingCity, textPhoneNumber, textSocialMediaAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buttonMainMenu = findViewById(R.id.btn_main_menu);
        textName = findViewById(R.id.name);
        textLastname = findViewById(R.id.lastname);
        textSchoolRegistrationYear = findViewById(R.id.school_registration_year);
        textGraduationYear = findViewById(R.id.graduation_year);
        textSchoolName = findViewById(R.id.schoolname);
        textDegree = findViewById(R.id.degree);
        textWorkingCountry = findViewById(R.id.working_country);
        textWorkingCity = findViewById(R.id.working_city);
        textPhoneNumber = findViewById(R.id.phone_number);
        textSocialMediaAccount = findViewById(R.id.social_media_account);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();

        CollectionReference profiles = db.collection("profiles");

        if(auth.getCurrentUser() == null)
        {
            Toast.makeText(ProfileActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }else{

            DocumentReference docRef = profiles.document(user.getEmail());

            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (!document.exists()) {
                            //Profile does not exist
                            //Redirect to create profile activity
                            Intent intent = new Intent(getApplicationContext(), CreateProfileActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            //profile is exist
                            //show attributes
                            textName.setText(document.get("name", String.class));
                            textLastname.setText(document.get("lastname", String.class));
                            textSchoolRegistrationYear.setText(document.get("registrationYear", String.class));
                            textGraduationYear.setText(document.get("graduationYear", String.class));
                            textSchoolName.setText(document.get("schoolName", String.class));
                            textDegree.setText(document.get("degree", String.class));
                            textWorkingCountry.setText(document.get("workingCountry", String.class));
                            textWorkingCity.setText(document.get("workingCity", String.class));
                            textPhoneNumber.setText(document.get("phoneNumber", String.class));
                            textSocialMediaAccount.setText(document.get("socialMediaAccount", String.class));
                        }
                    }else{
                        Toast.makeText(ProfileActivity.this, "Couldn't retrieve datas.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        buttonMainMenu.setOnClickListener(new View.OnClickListener()
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