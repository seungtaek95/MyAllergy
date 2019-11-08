package com.example.myallergy.Retrofit2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebEndPoint {
    String URL = "http://54.180.102.238:8080/";

    //약정보 url
    @GET("/MyAllergy/medicine")
    Call<List<MedicineVO>> searchMedicine(@Query("mname") String mname);

    //상품정보 url
    @GET("/MyAllergy/product")
    Call<List<ProductVO>> searchProductName(@Query("type") String type,
                                            @Query("pname") String pname); //이름으로 검색
    @GET("/MyAllergy/product")
    Call<List<ProductVO>> searchProductCategory(@Query("type") String type,
                                            @Query("category") String category); //카테고리 검색
    @GET("MyAllergy/product")
    Call<ProductVO> searchProductBarcode(@Query("type") String type,
                                         @Query("barcode") String barcode); //바코드 검색

    //커뮤니티 url
    @GET("MyAllergy/community")
    Call<List<PostVO>> searchCommunity();

    @POST("MyAllergy/community")
    Call<PostVO> sendCommunity(@Body PostVO post);

    //유저 닉네임 중복 검사 url
    @GET("MyAllergy/user")
    Call<Void> checkNickname(@Query("nickname") String nickname);
}
