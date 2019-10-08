package com.example.myallergy.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AllergyDAO {
    @Insert
    public void insert(Allergy allergy);

    @Query("DELETE FROM allergy")
    public void deleteAllergy();

    @Query("SELECT * FROM allergy")
    public List<Allergy> getAllergyList();
}
