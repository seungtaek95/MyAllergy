package com.example.myallergy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myallergy.Adapter.MyMedicineAdapter;
import com.example.myallergy.DataBase.Medicine;
import com.example.myallergy.DataBase.MedicineDAO;
import com.example.myallergy.DataBase.UserDataBase;
import com.example.myallergy.R;

import java.util.List;

public class MyMedicineActivity extends AppCompatActivity {
    private UserDataBase db;
    private MedicineDAO medicineDAO;
    private ListView listView;
    private LinearLayout layout;
    private MyMedicineAdapter medicineAdapter;
    private TextView addMyMedicine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_medicine);

        initializeDB();
        initializeView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //db에서 내 복용약 정보를 가져와서 어댑터에 추가
        getMyMedicineToAdapter();
        //리스트뷰에 어댑터 설정
        setListView(medicineAdapter);
    }

    private void initializeDB () {
        //database, dao 초기화
        db = UserDataBase.getInstance(getApplicationContext());
        medicineDAO = db.getMedicineDAO();
    }

    private void initializeView() {
        medicineAdapter = new MyMedicineAdapter();
        listView = findViewById(R.id.my_medicine_list);
        layout = findViewById(R.id.my_medicine_nothing);

        addMyMedicine = findViewById(R.id.my_medicine_add);
        //약 추가 버튼 클릭
        addMyMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MedicineSearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setListView(MyMedicineAdapter medicineAdapter) {
        listView.setAdapter(medicineAdapter);
    }

    private void getMyMedicineToAdapter () {
        new Thread() {
            public void run() {
                //db에서 약 정보를 어댑터에 추가
                List<Medicine> medicineList = medicineDAO.getMedicineList();
                if (medicineList.isEmpty()) {
                    createNoMedicineLayout();
                    return;
                }
                medicineAdapter.updateMedicineList(medicineList);
            }
        }.start();
    }

    private void createNoMedicineLayout() {
        TextView textView = new TextView(getApplicationContext());
        textView.setText("내 복용약 없음");
        textView.setTextSize(30);
        layout.addView(textView);
    }
}