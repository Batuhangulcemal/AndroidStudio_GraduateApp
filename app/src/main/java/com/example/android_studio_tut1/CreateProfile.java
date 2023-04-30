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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateProfile extends AppCompatActivity
{

    TextInputEditText editTextName, editTextLastname, editTextSchoolRegistrationYear, editTextGraduationYear, editTextSchoolName, editTextDegree, editTextWorkingCountry, editTextWorkingCity, editTextPhoneNumber, editTextSocialMediaAccount;
    Button buttonCreateProfile;
    FirebaseAuth auth;
    FirebaseFirestore db;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        editTextName = findViewById(R.id.name);
        editTextLastname = findViewById(R.id.lastname);
        editTextSchoolRegistrationYear = findViewById(R.id.school_registration_year);
        editTextGraduationYear = findViewById(R.id.graduation_year);
        editTextSchoolName = findViewById(R.id.schoolname);
        editTextDegree = findViewById(R.id.degree);
        editTextWorkingCountry = findViewById(R.id.working_country);
        editTextWorkingCity = findViewById(R.id.working_city);
        editTextPhoneNumber = findViewById(R.id.phone_number);
        editTextSocialMediaAccount = findViewById(R.id.social_media_account);
        buttonCreateProfile = findViewById(R.id.btn_create_profile);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();

        CollectionReference profiles = db.collection("profiles");

        if(user == null)
        {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
            return;
        }

        buttonCreateProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String name, lastname, registrationYear, graduationYear, schoolName, degree, workingCountry, workingCity, phoneNumber, socialMediaAccount;

                name = editTextName.getText().toString();
                lastname = editTextLastname.getText().toString();
                registrationYear = editTextSchoolRegistrationYear.getText().toString();
                graduationYear = editTextGraduationYear.getText().toString();
                schoolName = editTextSchoolName.getText().toString();
                degree = editTextDegree.getText().toString();
                workingCountry = editTextWorkingCountry.getText().toString();
                workingCity = editTextWorkingCity.getText().toString();
                phoneNumber = editTextPhoneNumber.getText().toString();
                socialMediaAccount = editTextSocialMediaAccount.getText().toString();

                if(TextUtils.isEmpty(name))
                {
                    Toast.makeText(CreateProfile.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(lastname))
                {
                    Toast.makeText(CreateProfile.this, "Please Enter Last Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(registrationYear))
                {
                    Toast.makeText(CreateProfile.this, "Please Enter Registration Year", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(graduationYear))
                {
                    Toast.makeText(CreateProfile.this, "Please Enter Graduation Year", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(schoolName))
                {
                    Toast.makeText(CreateProfile.this, "Please Enter School Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(degree))
                {
                    Toast.makeText(CreateProfile.this, "Please Enter Degree", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(workingCountry))
                {
                    Toast.makeText(CreateProfile.this, "Please Enter Working Country", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(workingCity))
                {
                    Toast.makeText(CreateProfile.this, "Please Enter Working City", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(phoneNumber))
                {
                    Toast.makeText(CreateProfile.this, "Please Enter Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> profileData = new HashMap<>();
                profileData.put("name", name);
                profileData.put("lastname", lastname);
                profileData.put("registrationYear", registrationYear);
                profileData.put("graduationYear", graduationYear);
                profileData.put("schoolName", schoolName);
                profileData.put("degree", degree);
                profileData.put("workingCountry", workingCountry);
                profileData.put("workingCity", workingCity);
                profileData.put("phoneNumber", phoneNumber);
                profileData.put("socialMediaAccount", socialMediaAccount);
                profiles.document(user.getEmail().toString())
                        .set(profileData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(CreateProfile.this, "Success", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CreateProfile.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });




            }
        });
    }
}