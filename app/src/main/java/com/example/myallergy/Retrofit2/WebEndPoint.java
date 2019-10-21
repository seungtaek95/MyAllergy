package com.example.myallergy.Retrofit2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebEndPoint {
    String URL = "http://54.180.102.238:8080/";

    //약정보 url
    @GET("/MyAllergy/medicine")
    Call<List<MedicineVO>> searchMedicine(@Query("mname") String mname);

    //상품정보 url
    @GET("/MyAllergy/product")
    Call<List<ProductVO>> searchProductName(@Query("type") String type,
                                            @Query("pname") String pname);
    @GET("MyAllergy/product")
    Call<ProductVO> searchProductBarcode(@Query("type") String type,
                                         @Query("barcode") String barcode);

    //커뮤니티 url
    @GET("MyAllergy/community")
    Call<List<CommunityVO>> searchCommunity(@Query("type") String type);

}
