package com.example.myallergy.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myallergy.R;
import com.example.myallergy.Retrofit2.CommunityVO;
import com.example.myallergy.Retrofit2.ProductVO;
import com.example.myallergy.Retrofit2.WebEndPoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragCommunity extends Fragment {
    TextView content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        initializeView(view);
        getComminity();

        return view;
    }
<<<<<<< HEAD
}
=======

    private void initializeView(View view) {
        content = view.findViewById(R.id.content);

    }

    private WebEndPoint getEndPoint() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebEndPoint.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebEndPoint endPoint = retrofit.create(WebEndPoint.class);
        return endPoint;
    }

    private void getComminity () {
        WebEndPoint endPoint = getEndPoint();

        endPoint.searchCommunity("").enqueue(new Callback<List<CommunityVO>>() {
            @Override
            public void onResponse(Call<List<CommunityVO>> call, Response<List<CommunityVO>> response) {
                List<CommunityVO> list = response.body();
                content.setText(list.get(0).getContent());
            }
            @Override
            public void onFailure(Call<List<CommunityVO>> call, Throwable t) {
            }
        });
    }
}
>>>>>>> upstream/master
