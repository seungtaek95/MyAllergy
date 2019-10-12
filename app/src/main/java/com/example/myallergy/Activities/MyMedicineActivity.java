package com.example.myallergy.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.myallergy.Adapter.MyMedicineAdapter;
import com.example.myallergy.DataBase.Medicine;
import com.example.myallergy.DataBase.MedicineDAO;
import com.example.myallergy.DataBase.UserDataBase;
import com.example.myallergy.R;

import java.util.List;

public class MyMedicineActivity extends AppCompatActivity {
    UserDataBase db;
    MedicineDAO medicineDAO;
    ListView listView;
    MyMedicineAdapter medicineAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_medicine);

        //database, dao 초기화
        db = UserDataBase.getInstance(getApplicationContext());
        medicineDAO = db.getMedicineDAO();

        //리스트뷰에 어댑터 설정
        medicineAdapter = new MyMedicineAdapter();
        listView = findViewById(R.id.my_medicine_list);
        listView.setAdapter(medicineAdapter);

        //db에서 내 복용약 정보를 가져와서 어댑터에 추가
        getMyMedicineToAdapter();
    }

    public void getMyMedicineToAdapter () {
        new Thread() {
            public void run() {
                //db에서 약 정보를 어댑터에 추가
                List<Medicine> medicineList = medicineDAO.getMedicineList();
                for(int i = 0; i < medicineList.size(); i++) {
                    medicineAdapter.addMedicine(medicineList.get(i));
                }
            }
        }.start();
    }
}