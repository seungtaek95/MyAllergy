package com.example.myallergy.DataBase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//allergy 테이블, 컬럼=알러지이름, 아이디값
@Entity(tableName = "allergy")
public class Allergy {
    @PrimaryKey
    @NonNull
    private int allergyId;
    private String allergyName;

    @NonNull
    public int getAllergyId() {
        return allergyId;
    }
    public void setAllergyId(@NonNull int allergyId) {
        this.allergyId = allergyId;
    }
    public String getAllergyName() {
        return allergyName;
    }
    public void setAllergyName(String allergyName) {
        this.allergyName = allergyName;
    }
}
