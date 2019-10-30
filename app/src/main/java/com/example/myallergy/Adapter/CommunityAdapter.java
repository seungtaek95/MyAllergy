package com.example.myallergy.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myallergy.R;
import com.example.myallergy.Retrofit2.PostVO;

import java.util.ArrayList;
import java.util.List;

public class CommunityAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<PostVO> communityList;
    private TextView tViewWriter, tViewDate, tViewTitle;

    public CommunityAdapter(){
        communityList = new ArrayList<>();
    }

    public void setCommunityList(List<PostVO> communityList){
        this.communityList = communityList;
    }

    @Override
    public int getCount() {
        return communityList.size();
    }

    @Override
    public PostVO getItem(int position) {
        return communityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();
        //뷰 초기화
        if(convertView == null) {
            mLayoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mLayoutInflater.inflate(R.layout.item_community, viewGroup, false);
        }
        initializeViews(convertView);
        setTextCommunity(position);

        return convertView;
    }

    private void initializeViews(View view) {
        tViewTitle = view.findViewById(R.id.post_title);
        tViewDate = view.findViewById(R.id.community_date);
        tViewWriter = view.findViewById(R.id.post_writer);
    }

    private void setTextCommunity(int position) {
        tViewWriter.setText(communityList.get(position).getWriter());
        tViewDate.setText(communityList.get(position).getDate());
        tViewTitle.setText(communityList.get(position).getTitle());
    }
}

