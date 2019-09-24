package com.example.myallergy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myallergy.Barcode.BarcodeMainActivity;
import com.example.myallergy.R;

public class FragHome extends Fragment {
    Button btnBarcode;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnBarcode= (Button)view.findViewById(R.id.barcode_btn);
        btnBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bintent= new Intent(getActivity(), BarcodeMainActivity.class);
                startActivity(bintent);
            }
        });
        return view;
    }
}
