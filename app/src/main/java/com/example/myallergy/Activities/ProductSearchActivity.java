package com.example.myallergy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.myallergy.R;
import com.example.myallergy.Retrofit2.ProductVO;
import com.example.myallergy.Retrofit2.WebEndPoint;
import com.example.myallergy.SearchResultView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductSearchActivity extends AppCompatActivity {
    private EditText eText;
    private ImageButton imageButtonSearch;
    private LinearLayout layout;
    private SearchResultView resultView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);

        initializeViews();
        searchProduct(getPnameFromIntent());

        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard(view);
                layout.removeAllViews();
                searchProduct(eText.getText().toString());
            }
        });
    }

    private void initializeViews() {
        eText = findViewById(R.id.eText_search_product);
        imageButtonSearch = findViewById(R.id.imageButton_search_product);
        layout = findViewById(R.id.layout_product_search);
    }

    private String getPnameFromIntent() {
        Intent intent = getIntent();
        return intent.getStringExtra("pname");
    }

    private WebEndPoint getEndPoint() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebEndPoint.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebEndPoint endPoint = retrofit.create(WebEndPoint.class);
        return endPoint;
    }

    private void searchProduct (String pname) {
        WebEndPoint endPoint = getEndPoint();
        resultView = new SearchResultView();

        if(pname == "") {
            createResultView(null);
            return;
        }
        endPoint.searchProductName("pname", pname).enqueue(new Callback<List<ProductVO>>() {
            @Override
            public void onResponse(Call<List<ProductVO>> call, Response<List<ProductVO>> response) {
                List<ProductVO> list = response.body();
                createResultView(list);
            }
            @Override
            public void onFailure(Call<List<ProductVO>> call, Throwable t) {
            }
        });
    }

    private void createResultView(List<ProductVO> list) {
        //검색결과 레이아웃 생성
        if (list.isEmpty()) {
            layout.addView(resultView
                    .createNothingFoundResult(getApplicationContext()));
            return;
        }
        for (ProductVO product : list) {
            layout.addView(resultView
                    .createResultTextProduct(getApplicationContext(), product));
        }
    }

    //키보드 숨기기
    private void hideKeyBoard (View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
