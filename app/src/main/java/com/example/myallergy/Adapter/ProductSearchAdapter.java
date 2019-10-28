package com.example.myallergy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myallergy.R;
import com.example.myallergy.Retrofit2.ProductVO;


import java.util.ArrayList;
import java.util.List;


public class ProductSearchAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<ProductVO> productList;
    private TextView textView;

    public ProductSearchAdapter() {
        productList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return productList.size();
    }
    @Override
    public ProductVO getItem(int position) {
        return productList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        //뷰 초기화
        if(convertView == null) {
            mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mLayoutInflater.inflate(R.layout.item_product_search, viewGroup, false);
        }
        initializeViews(convertView);
        if(getCount() == 0) {
            createNoListView(context);
        } else {
            createLIstViewItem(position, context);
        }

        return convertView;
    }

    private void initializeViews(View view) {
        textView = view.findViewById(R.id.product_search_pname);
    }

    public void setProductList(List<ProductVO> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    private void createNoListView(final Context context) {
        textView.setText("검색결과가 없습니다");
    }

    private void createLIstViewItem(final int position, final Context context) {
        //상품 이름으로 textview 설정
        final String medicineName = productList.get(position).getPname();
        textView.setText(medicineName);
    }
}