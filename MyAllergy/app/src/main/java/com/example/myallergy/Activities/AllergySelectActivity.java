package com.example.myallergy.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.myallergy.DataBase.Allergy;
import com.example.myallergy.DataBase.AllergyDAO;
import com.example.myallergy.DataBase.UserDataBase;
import com.example.myallergy.R;

import java.util.ArrayList;
import java.util.List;

public class AllergySelectActivity extends AppCompatActivity {
    final int ALLERGY_COUNT = 22;
    UserDataBase db;
    AllergyDAO allergyDAO;

    List<CheckBox> checkBoxList;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergy_check);

        //checkbox, button 초기화
        initializeCheckBoxes();
        btnSubmit = (Button)findViewById(R.id.allergy_submit);

        //database, dao 초기화
        db = UserDataBase.getInstance(getApplicationContext());
        allergyDAO = db.getAllergyDAO();

        //이전에 설정된 알러지 정보가 있으면 체크박스 체크 설정
        isAllergyTableExist();

        //설정완료 클릭 시 알러지 정보 insert
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertAllergy();
                finish();
            }
        });
    }

    public void initializeCheckBoxes () {
        //ceckbox를 담을 lsit
        checkBoxList = new ArrayList<>();

        //checkbox 22개 생성 후 리스트에 add
        for (int i = 0; i < ALLERGY_COUNT; i++) {
            int resId = getResources().getIdentifier("check" + (i + 1), "id", getPackageName());
            CheckBox tempCB = (CheckBox)findViewById(resId);
            checkBoxList.add(tempCB);
        }
    }

    //알러지 정보 테이블이 존재하는지
    public void isAllergyTableExist () {
        new Thread() {
            public void run() {
                //존재한다면 기존 알러지 정보를 checkbox에 체크
                if(allergyDAO.getAllergyList() != null) {
                    for(int i = 0; i < allergyDAO.getAllergyList().size(); i++) {
                        setCheckBoxStatus(allergyDAO.getAllergyList().get(i));
                    }
                }
            }
        }.start();
    }

    //기존 알러지 정보 checkbox에 체크
    public void setCheckBoxStatus (final Allergy allergy) {
        for(int i = 0; i < ALLERGY_COUNT; i++) {
            if(getCheckedAllergyId(i) == allergy.getAllergyId()) {
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
        new Thread() {
            public void run() {
                //기존 데이터 삭제
                allergyDAO.deleteAllergy();

                for(int i = 0; i < ALLERGY_COUNT; i++) {
                    if(checkBoxList.get(i).isChecked()) {
                        Allergy tempAllergy= new Allergy();
                        tempAllergy.setAllergyName(getCheckedAllergy(i));
                        tempAllergy.setAllergyId(getCheckedAllergyId(i));
                        allergyDAO.insert(tempAllergy);
                    }
                }
            }
        }.start();
    }
}
