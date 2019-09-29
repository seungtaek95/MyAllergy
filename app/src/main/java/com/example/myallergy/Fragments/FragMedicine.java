package com.example.myallergy.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myallergy.Activities.MedicineInfoActivity;
import com.example.myallergy.R;
import com.example.myallergy.Retrofit2.MedicineVO;
import com.example.myallergy.Retrofit2.WebEndPoint;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragMedicine extends Fragment {
    EditText eText;
    ImageButton btnSearch;
    LinearLayout layout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicine, container, false);
        initializeView(view);

        //검색 버튼 클릭
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard();           //키보드 숨기기
                layout.removeAllViews();  //검색 화면 초기화
                searchMedicine();         //약 검색
            }
        });
        return view;
    }

    //view 객체 초기화
    public void initializeView(View view) {
        //약 검색 창
        eText = (EditText)view.findViewById(R.id.medicine_editText);
        //검색 버튼
        btnSearch = (ImageButton)view.findViewById(R.id.medicine_btn_search);
        //결과 레이아웃
        layout = (LinearLayout)view.findViewById(R.id.medicine_layout_search_result);
    }

    //약 검색
    public void searchMedicine () {
        //retrofit으로 통신
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebEndPoint.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebEndPoint endPoints = retrofit.create(WebEndPoint.class);

        //edit text에서 문자열 가져와서 비동기로 통신
        endPoints.searchMedicine(eText.getText().toString()).enqueue(new Callback<List<MedicineVO>>() {
            @Override
            public void onResponse(Call<List<MedicineVO>> call, Response<List<MedicineVO>> response) {
                List<MedicineVO> list = response.body();
                //검색결과 레이아웃 생성
                for(int i = 0; i < list.size(); i++) {
                    createSearchResult(list.get(i));
                }
            }
            @Override
            public void onFailure(Call<List<MedicineVO>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    //검색 결과 textview 생성
    public void createSearchResult (final MedicineVO medicine) {
        TextView resultText = new TextView(getContext());
        resultText.setPadding(30, 30, 30, 30);
        resultText.setTextSize(30);
        resultText.setText(medicine.getPname());
        //클릭 시 상세정보 화면 전환
        resultText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MedicineInfoActivity.class);
                intent.putExtra("MEDICINE", medicine);
                startActivity(intent);
            }
        });
        //레이아웃에 추가
        layout.addView(resultText);
    }

    //키보드 숨기기
    public void hideKeyBoard () {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
