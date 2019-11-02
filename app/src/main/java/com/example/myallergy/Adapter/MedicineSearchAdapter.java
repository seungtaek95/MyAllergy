package com.example.myallergy.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myallergy.R;
import com.example.myallergy.Retrofit2.MedicineVO;

import java.util.ArrayList;
import java.util.List;

public class MedicineSearchAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<MedicineVO> medicineVOList;
    private TextView tViewMname;

    public MedicineSearchAdapter() {
        medicineVOList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return medicineVOList.size();
    }

    @Override
    public MedicineVO getItem(int position) {
        return medicineVOList.get(position);
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
            convertView = mLayoutInflater.inflate(R.layout.item_medicine_search, viewGroup, false);
        }
        initializeViews(convertView);
        if(getCount() == 0) {
            createNoListView();
        } else {
            createLIstViewItem(position);
        }

        return convertView;
    }

    public void setMedicineVOList(List<MedicineVO> medicineVOList) {
        this.medicineVOList = medicineVOList;
        notifyDataSetChanged();
    }

    private void initializeViews(View view) {
        tViewMname = view.findViewById(R.id.medicine_search_mname);
    }

    private void createNoListView() {
        tViewMname.setText("내 복용약이 없습니다");
        tViewMname.setClickable(false);
    }

    private void createLIstViewItem(int position) {
        tViewMname.setText(medicineVOList.get(position).getMname());
    }
}
