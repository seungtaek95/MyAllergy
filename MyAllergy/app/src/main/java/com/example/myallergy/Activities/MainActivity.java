package com.example.myallergy.Activities;

import android.content.Intent;
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
import com.nhn.android.naverlogin.OAuthLogin;

public class MainActivity extends AppCompatActivity {
    OAuthLogin mOAuthLogin;
    String token;

    BottomNavigationView bottomNavigationView;
    Fragment fragHome, fragMedicine, fragCommunity, fragSetting;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createHomeScreen();

        //네이버 로그인 사용자 인스턴스 가져오기
        mOAuthLogin = OAuthLogin.getInstance();
        token = mOAuthLogin.getAccessToken(getApplicationContext());

        if(token == null) { //로그인 안된 상태라면
            Intent intent = new Intent(this, NaverLoginActivity.class);
            startActivity(intent); //로그인 activity 실행
        }
    }

    //홈화면, 네비게이션 바 생성
    private void createHomeScreen () {
        //네비게이션 바 생성
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationListener);
        //네비게이션 바 고정
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);

        //프래그먼트 매니저
        fm = getSupportFragmentManager();

        //시작할 때 홈화면 띄우기
        fragHome = new FragHome();
        fm.beginTransaction().replace(R.id.main_frame, fragHome).commit();
    }

    //BottomNavigationView의 항목들이 선택됐을 때, Fragment를 변경
    private BottomNavigationView.OnNavigationItemSelectedListener navigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(fragHome == null) {
                        fragHome = new FragHome();
                        addFragment(fragHome);
                    }
                    showFragment(fragHome);
                    hideFragment(fragMedicine, fragCommunity, fragSetting);
                    return true;
                case R.id.navigation_medicine:
                    if(fragMedicine == null) {
                        fragMedicine = new FragMedicine();
                        addFragment(fragMedicine);
                    }
                    showFragment(fragMedicine);
                    hideFragment(fragHome, fragCommunity, fragSetting);
                    return true;
                case R.id.navigation_community:
                    if(fragCommunity == null) {
                        fragCommunity = new FragCommunity();
                        addFragment(fragCommunity);
                    }
                    showFragment(fragCommunity);
                    hideFragment(fragHome, fragMedicine, fragSetting);
                    return true;
                case R.id.navigation_setting:
                    if(fragSetting == null) {
                        fragSetting = new FragSetting();
                        addFragment(fragSetting);
                    }
                    showFragment(fragSetting);
                    hideFragment(fragHome, fragMedicine, fragCommunity);
                    return true;
            }
            return false;
        }
    };
    public void addFragment(Fragment frag) {
        fm.beginTransaction().add(R.id.main_frame, frag).commit();
    }
    public void showFragment(Fragment frag) {
        if(frag != null) fm.beginTransaction().show(frag).commit();
    }
    public void hideFragment(Fragment... frag) {
        for(Fragment tempFrag : frag) {
            if (tempFrag != null) fm.beginTransaction().hide(tempFrag).commit();
        }
    }
}
