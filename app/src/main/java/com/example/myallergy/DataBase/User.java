package com.example.myallergy.DataBase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//user 테이블, 컬럼=알러지이름, 아이디값
@Entity(tableName = "user")
public class User {
    @PrimaryKey
    @NonNull
    private int allergyId;
    private String allergy;

    @NonNull
    public int getAllergyId() {
        return allergyId;
    }
    public void setAllergyId(@NonNull int allergyId) {
        this.allergyId = allergyId;
    }
    public String getAllergy() {
        return allergy;
    }
    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }
}
