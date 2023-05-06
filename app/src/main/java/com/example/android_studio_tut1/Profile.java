package com.example.android_studio_tut1;

import java.util.Date;

public class Profile
{
    public String name;
    public String lastname;
    public String registrationYear;
    public String graduationYear;
    public String schoolName;
    public String degree;
    public String workingCountry;
    public String workingCity;
    public String phoneNumber;
    public String socialMediaAccount;

    public Profile(String name, String lastname, String registrationYear, String graduationYear, String schoolName, String degree, String workingCountry, String workingCity, String phoneNumber, String socialMediaAccount)
    {
        this.name = name;
        this.lastname = lastname;
        this.registrationYear = registrationYear;
        this.graduationYear = graduationYear;
        this.schoolName = schoolName;
        this.degree = degree;
        this.workingCountry = workingCountry;
        this.workingCity = workingCity;
        this.phoneNumber = phoneNumber;
        this.socialMediaAccount = socialMediaAccount;
    }

    public Profile(){

    }
}
