package com.example.myallergy.DataBase;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//user 테이블, 컬럼 = 유저이름,
@Entity(tableName = "user")
public class User {
    @PrimaryKey
    @NonNull
    private String userName;

    @NonNull
    public String getUserName() {
        return userName;
    }
    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }
}
