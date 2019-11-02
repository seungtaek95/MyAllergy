package com.example.myallergy.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.myallergy.Adapter.MedicineSearchAdapter;
import com.example.myallergy.R;
import com.example.myallergy.Retrofit2.MedicineVO;
import com.example.myallergy.Retrofit2.WebEndPoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MedicineSearchActivity extends AppCompatActivity {
    private EditText eText;
    private ImageButton btnSearch;
    private List<MedicineVO> medicineVOList;
    private ListView listView;
    private MedicineSearchAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_search);
        initializeView();
    }

    //view 초기화
    private void initializeView() {
        eText = findViewById(R.id.medicine_editText);               //약 검색 창
        btnSearch = findViewById(R.id.medicine_btn_search);         //검색 버튼
        listView = findViewById(R.id.medicine_search_listView);
        adapter = new MedicineSearchAdapter();
        listView.setAdapter(adapter);

        //검색 버튼 클릭
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard();           //키보드 숨기기
                searchMedicine();         //약 검색
            }
        });
        setItemClickListener();
    }

    private WebEndPoint getEndPoint() {
        WebEndPoint endPoint = new Retrofit.Builder()
                .baseUrl(WebEndPoint.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WebEndPoint.class);
        return endPoint;
    }

    //약 검색
    private void searchMedicine () {
        WebEndPoint endPoints = getEndPoint();
        String mname = eText.getText().toString();

        //edit text에서 문자열 가져와서 비동기로 통신
        endPoints.searchMedicine(mname).enqueue(new Callback<List<MedicineVO>>() {
            @Override
            public void onResponse(Call<List<MedicineVO>> call, Response<List<MedicineVO>> response) {
                medicineVOList = response.body();
                adapter.setMedicineVOList(medicineVOList);
            }
            @Override
            public void onFailure(Call<List<MedicineVO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setItemClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MedicineInfoActivity.class);
                intent.putExtra("medicine", medicineVOList.get(position));
                startActivity(intent);
            }
        });
    }

    //키보드 숨기기
    private void hideKeyBoard () {
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
