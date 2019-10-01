package com.example.myallergy.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.myallergy.DataBase.User;
import com.example.myallergy.DataBase.UserDAO;
import com.example.myallergy.DataBase.UserDataBase;
import com.example.myallergy.R;

import java.util.ArrayList;
import java.util.List;

public class AllergySelectActivity extends AppCompatActivity {
    final int ALLERGY_COUNT = 22;
    UserDataBase db;
    UserDAO userDAO;

    List<CheckBox> checkBoxList;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergy_check);

        //checkbox, button 생성
        createCheckBoxes();
        btnSubmit = (Button)findViewById(R.id.allergy_submit);

        //database, dao 생성
        db = UserDataBase.getInstance(getApplicationContext());
        userDAO = db.getUserDAO();

        //설정된 알러지 정보가 있으면 체크박스 체크 설정
        if(userDAO.getUser() != null) {
            for(int i = 0; i < userDAO.getUser().size(); i++) {
                setCheckBoxStatus(userDAO.getUser().get(i));
            }
        }

        //설정완료 클릭 시 알러지 정보 insert
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDAO.deleteUser();
                insertAllergy();
                finish();
            }
        });
    }

    public void createCheckBoxes () {
        //ceckbox를 담을 lsit
        checkBoxList = new ArrayList<>();

        //checkbox 22개 생성 후 리스트에 add
        for (int i = 0; i < ALLERGY_COUNT; i++) {
            int resId = getResources().getIdentifier("check" + (i + 1), "id", getPackageName());
            CheckBox tempCB = (CheckBox)findViewById(resId);
            checkBoxList.add(tempCB);
        }
    }

    //checkbox 체크 여부 설정
    public void setCheckBoxStatus (User user) {
        for(int i = 0; i < ALLERGY_COUNT; i++) {
            //이미 저장된 사용자 알러지정보와 일치하면 체크됨으로 설정
            if(getCheckedAllergyId(i) == user.getAllergyId()) {
                checkBoxList.get(i).setChecked(true);
            }
        }
    }

    //checkBoxList에서 체크된 checkbox의 알러지 return
    public String getCheckedAllergy(int i) {
        return checkBoxList.get(i).getText().toString();
    }

    //checkBoxList에서 체크된 checkbox의 알러지아이디 return
    public int getCheckedAllergyId(int i) {
        return checkBoxList.get(i).getId();
    }

    //체크된 알러지 정보를 db에 저장
    public void insertAllergy () {
        for(int i = 0; i < ALLERGY_COUNT; i++) {
            if(checkBoxList.get(i).isChecked()) {
                User tempUser = new User();
                tempUser.setAllergy(getCheckedAllergy(i));
                tempUser.setAllergyId(getCheckedAllergyId(i));
                userDAO.insert(tempUser);
            }
        }
    }
}
