package com.example.myallergy.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.myallergy.Activities.AllergySelectActivity;
import com.example.myallergy.Activities.MainActivity;
import com.example.myallergy.DataBase.UserDAO;
import com.example.myallergy.DataBase.UserDataBase;
import com.example.myallergy.R;
import com.nhn.android.naverlogin.OAuthLogin;

public class FragSetting extends Fragment {
    LinearLayout btnSetAllergy;
    Button btnLogout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        //내 알러지 설정 버튼
        btnSetAllergy = (LinearLayout)view.findViewById(R.id.btn_setting_allergy);
        //로그아웃 버튼
        btnLogout = (Button)view.findViewById(R.id.btn_logout);

        //내 알러지 설정 버튼 클릭, 알러지 선택 activity 실행
        btnSetAllergy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AllergySelectActivity.class);
                startActivity(intent);
            }
        });
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
                                        //네이버 로그인 사용자 정보 삭제
                                        OAuthLogin mOAuthLogin = OAuthLogin.getInstance();
                                        mOAuthLogin.logoutAndDeleteToken(getContext());

                                        //db 데이터 초기화
                                        UserDataBase db = UserDataBase.getInstance(getContext());
                                        UserDAO userDAO = db.getUserDAO();
                                        userDAO.deleteUser();

                                        //actiity 초기화
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                        getActivity().overridePendingTransition(0, 0);

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
