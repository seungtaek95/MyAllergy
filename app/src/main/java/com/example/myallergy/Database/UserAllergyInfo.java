package com.example.myallergy.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "user")
public class UserAllergyInfo {
    @PrimaryKey
    public String allergyid;
    public String allergy;
}
