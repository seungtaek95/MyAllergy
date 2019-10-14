package com.example.myallergy.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
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
import com.example.myallergy.SearchResultView;


import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class FragMedicine extends Fragment {
    private EditText eText;
    private ImageButton btnSearch;
    private LinearLayout layout;
    private SearchResultView resultView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

    //view 초기화
    private void initializeView(View view) {
        eText = view.findViewById(R.id.medicine_editText);               //약 검색 창
        btnSearch = view.findViewById(R.id.medicine_btn_search);         //검색 버튼
        layout = view.findViewById(R.id.medicine_layout_search_result);  //결과 레이아웃
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
                    .createNothingFoundResult(getContext()));
            return;
        }
        for(MedicineVO medicine : list) {
            layout.addView(resultView
                    .createResultMedicine(getContext(), medicine));

        }
    }

    //키보드 숨기기
    private void hideKeyBoard () {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
