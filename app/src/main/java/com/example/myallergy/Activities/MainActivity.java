package com.example.myallergy.Activities;

import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myallergy.Fragments.FragCommunity;
import com.example.myallergy.Fragments.FragHome;
import com.example.myallergy.Fragments.FragMedicine;
import com.example.myallergy.Fragments.FragSetting;
import com.example.myallergy.R;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment fragHome, fragMedicine, fragCommunity, fragSetting;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //네비게이션 바 생성
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationListener);

        //네비게이션 바 고정
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        //fragment 4개
        fragHome = new FragHome();
        fragMedicine = new FragMedicine();
        fragCommunity = new FragCommunity();
        fragSetting = new FragSetting();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //프래그먼트 매니저
        fm = getSupportFragmentManager();
        //시작할 때 홈화면 띄우기
        fm.beginTransaction().replace(R.id.main_frame, fragHome).commit();
    }

    //BottomNavigationView의 항목들이 선택됐을 때, Fragment를 변경
    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fm.beginTransaction().replace(R.id.main_frame, fragHome).commit();
                    return true;
                case R.id.navigation_medicine:
                    fm.beginTransaction().replace(R.id.main_frame, fragMedicine).commit();
                    return true;
                case R.id.navigation_community:
                    fm.beginTransaction().replace(R.id.main_frame, fragCommunity).commit();
                    return true;
                case R.id.navigation_setting:
                    fm.beginTransaction().replace(R.id.main_frame, fragSetting).commit();
                    return true;
            }
            return false;
        }
    };
}
