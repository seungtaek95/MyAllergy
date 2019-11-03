package com.example.myallergy.DataBase;

import android.arch.persistence.room.Entity;
        import android.arch.persistence.room.PrimaryKey;
        import android.support.annotation.NonNull;

//medicine 테이블, 컬럼=알러지이름, 아이디값
@Entity(tableName = "medicine")
public class Medicine {
    @PrimaryKey
    @NonNull
    private String medicineName;

    @NonNull
    public String getMedicineName() {
        return medicineName;
    }
    public void setMedicineName(@NonNull String medicineName) {
        this.medicineName = medicineName;
    }
}