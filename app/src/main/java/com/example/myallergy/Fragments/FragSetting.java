package com.example.myallergy.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myallergy.Activities.MainActivity;
import com.example.myallergy.Activities.NaverLoginActivity;
import com.example.myallergy.R;
import com.nhn.android.naverlogin.OAuthLogin;

public class FragSetting extends Fragment {
    Button btnLogin, btnLogout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        //로그아웃 버튼
        btnLogout = (Button)view.findViewById(R.id.btn_logout);

        //로그아웃 버튼 클릭, 알림창 팝업
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("로그아웃");
                builder.setMessage("로그아웃 하시겠습니까?");

                //예 클릭
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new Thread() {
                                    public void run() {
                                        OAuthLogin mOAuthLogin = OAuthLogin.getInstance();
                                        mOAuthLogin.logoutAndDeleteToken(getContext());

                                        //로그아웃하면 로그인 activity 실행
                                        Intent intent = new Intent(getActivity(), NaverLoginActivity.class);
                                        startActivity(intent);
                                        //actiity 초기화
                                        getActivity().finish();
                                        intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                }.start();
                            }
                        });
                //아니오 클릭
                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.show();
            }
        });
        return view;
    }
}