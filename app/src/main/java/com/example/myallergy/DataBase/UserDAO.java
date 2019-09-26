package com.example.myallergy.DataBase;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    public void insert(User user);

    @Query("DELETE FROM user")
    public void deleteUser();

    @Query("SELECT * FROM user")
    public List<User> getUser();
}
