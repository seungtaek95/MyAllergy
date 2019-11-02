package com.example.myallergy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.myallergy.Adapter.ProductSearchAdapter;
import com.example.myallergy.R;
import com.example.myallergy.Retrofit2.ProductVO;
import com.example.myallergy.Retrofit2.WebEndPoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProductSearchActivity extends AppCompatActivity {
    private EditText eText;
    private ImageButton imageButtonSearch;
    private RecyclerView recyclerView;
    private ProductSearchAdapter productSearchAdapter;
    private LinearLayout layoutNoResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);

        initializeViews();
        getTypeAndSearch();
    }

    private void initializeViews() {
        eText = findViewById(R.id.eText_search_product);
        imageButtonSearch = findViewById(R.id.imageButton_search_product);

        recyclerView = findViewById(R.id.product_search_result_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //구분선 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        layoutNoResult = findViewById(R.id.product_search_no_result);

        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard(view);
                searchProductByPname(eText.getText().toString());
            }
        });
    }

    private void getTypeAndSearch() {
        Intent intent = getIntent();
        switch(intent.getStringExtra("type")) {
            case "pname":
                searchProductByPname(intent.getStringExtra("pname"));
                break;
            case "category":
                searchProductByCategory(intent.getStringExtra("category"));
                break;
        }
    }

    private WebEndPoint getEndPoint() {
        WebEndPoint endPoint = new Retrofit.Builder()
                .baseUrl(WebEndPoint.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WebEndPoint.class);
        return endPoint;
    }


    public void searchProductByPname (String pname) {
        WebEndPoint endPoint = getEndPoint();

        endPoint.searchProductName("pname", pname).enqueue(new Callback<List<ProductVO>>() {
            @Override
            public void onResponse(Call<List<ProductVO>> call, Response<List<ProductVO>> response) {
                if (response.body().size() == 0) {
                    showNoSearchResult();
                } else {
                    showSearchResult(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<ProductVO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void searchProductByCategory (String category) {
        WebEndPoint endPoint = getEndPoint();

        endPoint.searchProductCategory("category", category).enqueue(new Callback<List<ProductVO>>() {
            @Override
            public void onResponse(Call<List<ProductVO>> call, Response<List<ProductVO>> response) {
                showSearchResult(response.body());
            }
            @Override
            public void onFailure(Call<List<ProductVO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void showNoSearchResult() {
        recyclerView.setVisibility(View.INVISIBLE);
        layoutNoResult.setVisibility(View.VISIBLE);
    }

    private void showSearchResult(List<ProductVO> productVOList) {
        recyclerView.setVisibility(View.VISIBLE);
        layoutNoResult.setVisibility(View.INVISIBLE);
        productSearchAdapter = new ProductSearchAdapter(productVOList);
        recyclerView.setAdapter(productSearchAdapter);
    }
    //키보드 숨기기
    private void hideKeyBoard (View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
