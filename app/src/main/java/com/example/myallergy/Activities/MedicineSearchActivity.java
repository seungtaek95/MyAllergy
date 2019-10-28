package com.example.myallergy.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
<<<<<<< HEAD:app/src/main/java/com/example/myallergy/Fragments/FragMedicine.java
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
=======
import android.support.v7.app.AppCompatActivity;
import android.view.View;
>>>>>>> upstream/master:app/src/main/java/com/example/myallergy/Activities/MedicineSearchActivity.java
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.myallergy.R;
import com.example.myallergy.Retrofit2.MedicineVO;
import com.example.myallergy.Retrofit2.WebEndPoint;
import com.example.myallergy.SearchResultView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MedicineSearchActivity extends AppCompatActivity {
    private EditText eText;
    private ImageButton btnSearch;
    private LinearLayout layout;
    private SearchResultView resultView;

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
        layout = findViewById(R.id.medicine_layout_search_result);  //결과 레이아웃

        //검색 버튼 클릭
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard();           //키보드 숨기기
                layout.removeAllViews();  //검색 화면 초기화
                searchMedicine();         //약 검색
            }
        });
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
        resultView = new SearchResultView();

        //edit text에서 문자열 가져와서 비동기로 통신
        endPoints.searchMedicine(mname).enqueue(new Callback<List<MedicineVO>>() {
            @Override
            public void onResponse(Call<List<MedicineVO>> call, Response<List<MedicineVO>> response) {
                List<MedicineVO> list = response.body();
                createResultView(list);
            }
            @Override
            public void onFailure(Call<List<MedicineVO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void createResultView(List<MedicineVO> list) {
        if(list.isEmpty()) {
            layout.addView(resultView
                    .createNothingFoundResult(getApplicationContext()));
            return;
        }
        for(MedicineVO medicine : list) {
            layout.addView(resultView
                    .createResultMedicine(getApplicationContext(), medicine));

        }
    }

    //키보드 숨기기
    private void hideKeyBoard () {
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
