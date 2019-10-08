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
import android.widget.Toast;

import com.example.myallergy.Activities.BarcodeScannerActivity;
import com.example.myallergy.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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

        //바코드 스캔 버튼 클릭시 activity 전환
        btnBarcode= (Button)view.findViewById(R.id.barcode_btn);
        btnBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setCaptureActivity(BarcodeScannerActivity.class);
                integrator.initiateScan();
            }
        });
        return view;
    }

    //바코드 인식시 결과 화면
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d("onActivityResult", "onActivityResult: .");
        if (resultCode == Activity.RESULT_OK) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            String result = scanResult.getContents();
            Log.d("onActivityResult", "onActivityResult: ." + result);

            //바코드 번호 Taost 메세지
            Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
        }
    }
}
