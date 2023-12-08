package com.example.apiarcamento.models;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("name")
    public String name;
    @SerializedName("last_name")
    public String last_name;
    @SerializedName("mother_surname")
    public String mother_surname;
    @SerializedName("gender")
    public String gender;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;


    public User(String name, String last_name, String mother_surname, String gender, String email, String password) {
        this.name = name;
        this.last_name = last_name;
        this.mother_surname = mother_surname;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }



    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String name, String last_name, String mother_surname, String gender, String password) {
        this.name = name;
        this.last_name = last_name;
        this.mother_surname = mother_surname;
        this.gender = gender;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMother_surname() {
        return mother_surname;
    }

    public void setMother_surname(String mother_surname) {
        this.mother_surname = mother_surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
