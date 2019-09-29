package com.example.myallergy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.myallergy.R;
import com.example.myallergy.Retrofit2.MedicineVO;


public class MedicineInfoActivity extends AppCompatActivity {
    TextView pname, company, effect, capacity, ingredient, category, validate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_search_result_window);

        initializeView();
        setTextView();
    }

    public void initializeView() {
        pname = (TextView)findViewById(R.id.medicine_pname);
        company = (TextView)findViewById(R.id.medicine_company);
        effect = (TextView)findViewById(R.id.medicine_effect);
        capacity = (TextView)findViewById(R.id.medicine_capacity);
        ingredient = (TextView)findViewById(R.id.medicine_ingredient);
        category = (TextView)findViewById(R.id.medicine_category);
        validate = (TextView)findViewById(R.id.medicine_validate);
    }

    public void setTextView () {
        Intent intent = getIntent();
        MedicineVO medicine = (MedicineVO)intent.getSerializableExtra("MEDICINE");
        pname.setText(medicine.getPname());
        company.setText(medicine.getCompany());
        effect.setText(medicine.getEffect());
        capacity.setText(medicine.getCapacity());
        ingredient.setText(medicine.getIngredient());
        category.setText(medicine.getCategory());
        validate.setText(medicine.getValidate());
    }
}
