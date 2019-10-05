package com.example.myallergy.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myallergy.DataBase.Medicine;
import com.example.myallergy.DataBase.MedicineDAO;
import com.example.myallergy.DataBase.UserDataBase;
import com.example.myallergy.R;
import com.example.myallergy.Retrofit2.MedicineVO;

import java.util.List;


public class MedicineInfoActivity extends AppCompatActivity {
    TextView mname, company, effect, capacity, ingredient, category, validate;
    Button btnAdd;

    UserDataBase db;
    MedicineDAO medicineDAO;

    boolean isExist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_search_result_window);

        //view 초기화, 텍스트뷰 초기화
        initializeView();
        setTextView();

        //내 복용약 추가 버튼 클릭 시
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertMedicine();
                Toast.makeText(getApplicationContext(), "내 복용약에 추가되었습니다", Toast.LENGTH_SHORT).show();
                setButtonToDisabled();
            }
        });
    }

    public void initializeView() {
        mname = (TextView)findViewById(R.id.medicine_name);
        company = (TextView)findViewById(R.id.medicine_company);
        effect = (TextView)findViewById(R.id.medicine_effect);
        capacity = (TextView)findViewById(R.id.medicine_capacity);
        ingredient = (TextView)findViewById(R.id.medicine_ingredient);
        category = (TextView)findViewById(R.id.medicine_category);
        validate = (TextView)findViewById(R.id.medicine_validate);
        btnAdd = (Button)findViewById(R.id.medicine_add_my);

        //database, dao 초기화
        db = UserDataBase.getInstance(getApplicationContext());
        medicineDAO = db.getMedicineDAO();
    }

    public void setTextView () {
        //인텐트로 받아온 값들로 텍스트뷰 설정
        Intent intent = getIntent();
        MedicineVO medicine = (MedicineVO)intent.getSerializableExtra("MEDICINE");

        mname.setText(medicine.getMname());
        company.setText(medicine.getCompany());
        effect.setText(medicine.getEffect());
        capacity.setText(medicine.getCapacity());
        ingredient.setText(medicine.getIngredient());
        category.setText(medicine.getCategory());
        validate.setText(medicine.getValidate());

        isMedicineExist(medicine.getMname());
        if(isExist)
            setButtonToDisabled();
    }

    //내 복용약에 이미 추가돼있는지
    public void isMedicineExist(final String name) {
        Thread checkDB = new Thread(new Runnable() {
            @Override
            public void run() {
                List<Medicine> list = medicineDAO.getMedicineList();
                setIsExist(false);
                for (int i = 0; i < list.size(); i++) {
                    if (name.equals(list.get(i).getMedicineName())) {
                        setIsExist(true);
                        break;
                    }
                }
            }
        });
        checkDB.start();
        try {
            checkDB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //내 복용약에 추가 버튼 비활성화
    public void setButtonToDisabled() {
        btnAdd.setText("복용약에 추가되어있음");
        btnAdd.setBackgroundColor(Color.GRAY);
        btnAdd.setEnabled(false);
    }

    public void setIsExist(boolean isExist) {
        this.isExist = isExist;
    }
    //db의 medicine테이블에 내 복용약 추가
    public void insertMedicine() {
        new Thread() {
            public void run() {
                Medicine tempMedicine = new Medicine();
                tempMedicine.setMedicineName(mname.getText().toString());
                medicineDAO.insert(tempMedicine);
            }
        }.start();
    }
}
