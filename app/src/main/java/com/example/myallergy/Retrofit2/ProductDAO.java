package com.example.myallergy.Retrofit2;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDAO {
    private List<ProductVO> list;

    public List<ProductVO> getList() {
        return list;
    }
    public void setList(List<ProductVO> list) {
        this.list = list;
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

        if(pname == "") {
            return;
        }
        endPoint.searchProductName("pname", pname).enqueue(new Callback<List<ProductVO>>() {
            @Override
            public void onResponse(Call<List<ProductVO>> call, Response<List<ProductVO>> response) {
                setList(response.body());
            }
            @Override
            public void onFailure(Call<List<ProductVO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
