package com.example.myallergy.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myallergy.Activities.BarcodeScannerActivity;
import com.example.myallergy.Activities.CommunityContentActivity;
import com.example.myallergy.Activities.ProductInfoActivity;
import com.example.myallergy.Activities.ProductSearchActivity;
import com.example.myallergy.R;
import com.example.myallergy.Retrofit2.PostVO;
import com.example.myallergy.Retrofit2.ProductVO;
import com.example.myallergy.Retrofit2.WebEndPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragHome extends Fragment {
    private PostVO latestPost;
    private EditText eTextSearch;
    private ImageButton imageButtonSearch;
    private Button btnBarcode;
    private TextView title, writer, date, content;
    private LinearLayout layoutPost;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initializeViews(view);
        setButtonClickListener();
        getCommunity();

        return view;
    }

    private void initializeViews(View view) {
        eTextSearch = view.findViewById(R.id.eText_search_product);
        imageButtonSearch = view.findViewById(R.id.imageButton_search_product);
        btnBarcode= view.findViewById(R.id.barcode_btn);
        title = view.findViewById(R.id.home_post_title);
        writer = view.findViewById(R.id.home_post_writer);
        date = view.findViewById(R.id.home_post_date);
        content = view.findViewById(R.id.home_post_content);
        layoutPost = view.findViewById(R.id.home_latest_post);
    }

    private void setButtonClickListener() {
        //상품검색 버튼 클릭
        imageButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProductSearchActivity.class);
                intent.putExtra("type", "pname");
                intent.putExtra("pname", eTextSearch.getText().toString());
                startActivity(intent);
            }
        });
        //바코드 스캔 버튼 클릭시 activity 전환
        btnBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(FragHome.this);
                integrator.setCaptureActivity(BarcodeScannerActivity.class);
                integrator.initiateScan();
            }
        });
        //최근 글 클릭시
        layoutPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CommunityContentActivity.class);
                intent.putExtra("community", latestPost);
                startActivity(intent);
            }
        });
    }

    //바코드 인식시 결과 화면
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == Activity.RESULT_OK) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            String result = scanResult.getContents();

            searchProduct(result);
        }
    }

    private WebEndPoint getEndPoint() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebEndPoint.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebEndPoint endPoint = retrofit.create(WebEndPoint.class);
        return endPoint;
    }

    private void searchProduct (final String barcode) {
        WebEndPoint endPoint = getEndPoint();

        endPoint.searchProductBarcode("barcode", barcode).enqueue(new Callback<ProductVO>() {
            @Override
            public void onResponse(Call<ProductVO> call, Response<ProductVO> response) {
                ProductVO product = response.body();

                if (product.getPname() == null) {
                    Intent intent = new Intent(getContext(), ProductSearchActivity.class);
                    intent.putExtra("type", "pname");
                    intent.putExtra("pname", "");
                    startActivity(intent);
                } else {
                    Log.e("barcode Num", barcode);
                    Intent intent = new Intent(getContext(), ProductInfoActivity.class);
                    intent.putExtra("product", product);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<ProductVO> call, Throwable t) {
            }
        });
    }

    private void getCommunity () {
        WebEndPoint endPoint = getEndPoint();

        endPoint.searchCommunity().enqueue(new Callback<List<PostVO>>() {
            @Override
            public void onResponse(Call<List<PostVO>> call, Response<List<PostVO>> response) {
                setLatestPost(response.body().get(0));
                setLatestPostView();
            }
            @Override
            public void onFailure(Call<List<PostVO>> call, Throwable t) {
            }
        });
    }

    public void setLatestPost(PostVO latestPost) {
        this.latestPost = latestPost;
    }

    private void setLatestPostView() {
        title.setText(latestPost.getTitle());
        writer.setText(latestPost.getWriter());
        date.setText(latestPost.getDate());
        content.setText(latestPost.getContent());
    }
}
