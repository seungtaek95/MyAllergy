package com.example.myallergy.Activities;

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
    private List<ProductVO> productList;
    private EditText eText;
    private ImageButton imageButtonSearch;
    private ListView listView;
    private ProductSearchAdapter productSearchAdapter;

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

        productSearchAdapter = new ProductSearchAdapter();
        listView = findViewById(R.id.product_search_result_list);
        listView.setAdapter(productSearchAdapter);

        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard(view);
                searchProductByPname(eText.getText().toString());
            }
        });
        setItemClickListener();
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

    public void setProductList(List<ProductVO> productList) {
        this.productList = productList;
    }

    public void searchProductByPname (String pname) {
        WebEndPoint endPoint = getEndPoint();

        if(pname == "") {
            return;
        }
        endPoint.searchProductName("pname", pname).enqueue(new Callback<List<ProductVO>>() {
            @Override
            public void onResponse(Call<List<ProductVO>> call, Response<List<ProductVO>> response) {
                setProductList(response.body());
                productSearchAdapter.setProductList(productList);
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
                setProductList(response.body());
                productSearchAdapter.setProductList(productList);
            }
            @Override
            public void onFailure(Call<List<ProductVO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void setItemClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProductInfoActivity.class);
                intent.putExtra("product", productList.get(position));
                startActivity(intent);
            }
        });
    }

    //키보드 숨기기
    private void hideKeyBoard (View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
