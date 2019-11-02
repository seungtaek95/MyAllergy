package com.example.myallergy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.myallergy.Activities.CommunityContentActivity;
import com.example.myallergy.Activities.CommunityPostForm;
import com.example.myallergy.Adapter.CommunityAdapter;
import com.example.myallergy.R;
import com.example.myallergy.Retrofit2.PostVO;
import com.example.myallergy.Retrofit2.WebEndPoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragCommunity extends Fragment {
    private ListView listview;
    private List<PostVO> communityList;
    private ImageButton imgBtn;
    private CommunityAdapter communityAdapter;


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
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getCommunity();
    }

    private void initializeView(View view) {
        imgBtn = view.findViewById(R.id.post_button);
        communityAdapter = new CommunityAdapter();
        listview = view.findViewById(R.id.post_list_view);
        listview.setAdapter(communityAdapter);
        setClickListener();
    }


    private WebEndPoint getEndPoint() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebEndPoint.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebEndPoint endPoint = retrofit.create(WebEndPoint.class);
        return endPoint;
    }

    private void getCommunity () {
        WebEndPoint endPoint = getEndPoint();

        endPoint.searchCommunity().enqueue(new Callback<List<PostVO>>() {
            @Override
            public void onResponse(Call<List<PostVO>> call, Response<List<PostVO>> response) {
                setCommunityList(response.body());
                communityAdapter.setCommunityList(communityList);
            }
            @Override
            public void onFailure(Call<List<PostVO>> call, Throwable t) {
            }
        });
    }

    public void setCommunityList(List<PostVO> communityList) {
        this.communityList = communityList;
    }

    private void setClickListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CommunityContentActivity.class);
                intent.putExtra("community", communityList.get(position));
                startActivity(intent);
            }
        });

        // 작성버튼 클릭
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CommunityPostForm.class);
                startActivity(intent);
            }
        });
    }
}
