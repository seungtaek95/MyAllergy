package com.example.myallergy.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(version = 1, entities = {UserAllergyInfo.class})
abstract class MyAllergyDB extends RoomDatabase {

}
