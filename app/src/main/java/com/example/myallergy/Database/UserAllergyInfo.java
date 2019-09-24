package com.example.myallergy.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user")
public class UserAllergyInfo {
    @PrimaryKey
    @NonNull
    public String allergyid;
    public String allergy;
}
