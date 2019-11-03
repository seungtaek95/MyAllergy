package com.example.myallergy.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
    public static Context mContext;
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
        mContext = this;

        initializeDB();
        initializeView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //db에서 내 복용약 정보를 가져와서 어댑터에 추가
        getMyMedicineToAdapter();
        //리스트뷰에 어댑터 설정
        listView.setAdapter(medicineAdapter);
    }

    public static Context getmContext() {
        if (mContext == null) {
            mContext = new MyMedicineActivity();
        }
        return mContext;
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

    private void getMyMedicineToAdapter () {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                //db에서 약 정보를 어댑터에 추가
                List<Medicine> medicineList = medicineDAO.getMedicineList();
                medicineAdapter.updateMedicineList(medicineList);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        medicineAdapter.notifyDataSetChanged();
        if (medicineAdapter.isEmpty()) {
            viewNoMedicineText();
        } else {
            hideNoMedicineText();
        }
    }

    public void viewNoMedicineText() {
        layout.setVisibility(View.VISIBLE);
    }

    private void hideNoMedicineText() {
        layout.setVisibility(View.INVISIBLE);
    }
}