package com.example.myallergy.DataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MedicineDAO {
    @Insert
    public void insert(Medicine medicine);

    @Delete
    public void deleteMedicine(Medicine... medicine);

    @Query("DELETE FROM medicine")
    public void deleteAllMedicine();

    @Query("SELECT * FROM medicine")
    public List<Medicine> getMedicineList();
}
