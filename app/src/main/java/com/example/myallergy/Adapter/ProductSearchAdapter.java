package com.example.myallergy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myallergy.Activities.ProductInfoActivity;
import com.example.myallergy.R;
import com.example.myallergy.Retrofit2.ProductVO;


import java.util.ArrayList;
import java.util.List;


public class ProductSearchAdapter extends RecyclerView.Adapter<ProductSearchAdapter.ViewHolder> {
    List<ProductVO> productVOList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tViewPname;
        private ImageView imageView;

        ViewHolder(final View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_search_image);
            tViewPname = itemView.findViewById(R.id.product_search_pname);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition() ;
                    if (position != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(itemView.getContext(), ProductInfoActivity.class);
                        intent.putExtra("product", productVOList.get(position));
                        itemView.getContext().startActivity(intent);
                    }
                }
            });
        }
    }

    public ProductSearchAdapter(List<ProductVO> productVOList) {
        this.productVOList = productVOList;
    }

    @NonNull
    @Override
    public ProductSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_product_search, viewGroup, false);
        ProductSearchAdapter.ViewHolder viewHolder = new ProductSearchAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSearchAdapter.ViewHolder viewHolder, int position) {
        Glide.with(viewHolder.imageView.getContext()).load(productVOList.get(position).getImgurl1()).into(viewHolder.imageView);
        viewHolder.tViewPname.setText(productVOList.get(position).getPname());
    }

    @Override
    public int getItemCount() {
        return productVOList.size();
    }
}