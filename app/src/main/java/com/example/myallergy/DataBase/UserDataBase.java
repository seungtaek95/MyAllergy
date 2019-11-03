package com.example.myallergy.DataBase;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(version = 1, entities = {Medicine.class, Allergy.class, User.class}, exportSchema = false)
public abstract class UserDataBase extends RoomDatabase {
    private static UserDataBase INSTANCE;
    private static final Object sLock = new Object();

    //싱글톤으로 데이터베이스 인스턴스 생성
    public static UserDataBase getInstance(Context context) {
        synchronized (sLock) {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), UserDataBase.class, "user")
                        .build();
            }
            return INSTANCE;
        }
    }

    public abstract MedicineDAO getMedicineDAO();
    public abstract AllergyDAO getAllergyDAO();
    public abstract UserDAO getUserDAO();
}