package com.example.myallergy.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myallergy.DataBase.User;
import com.example.myallergy.DataBase.UserDAO;
import com.example.myallergy.DataBase.UserDataBase;
import com.example.myallergy.DataBase.UserProfile;
import com.example.myallergy.R;

public class CreateUserNameActivity extends AppCompatActivity {
    EditText eTextUserName;
    Button btnSubmit;
    UserDataBase db;
    UserDAO userDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_name);

        initializeView();
    }

    private void initializeView() {
        eTextUserName = findViewById(R.id.eText_create_user_name);
        btnSubmit = findViewById(R.id.btn_submit_user_name);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((eTextUserName.getText().toString()).equals("")) {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력하세요", Toast.LENGTH_LONG).show();
                } else {
                    initializeDB();
                    setUserName();
                    finish();
                }
            }
        });
    }

    private void initializeDB() {
        db = UserDataBase.getInstance(getApplicationContext());
        userDAO = db.getUserDAO();
    }

    private void setUserName() {
        new Thread() {
            public void run() {
                User user = new User();
                user.setUserName(eTextUserName.getText().toString());
                userDAO.insert(user);
            }
        }.start();
        UserProfile.userName = eTextUserName.getText().toString();
    }
}
