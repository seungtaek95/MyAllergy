package com.example.myallergy.Activities;

import android.content.Intent;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myallergy.DataBase.AllergyDAO;
import com.example.myallergy.DataBase.UserDAO;
import com.example.myallergy.DataBase.UserDataBase;
import com.example.myallergy.DataBase.UserProfile;
import com.example.myallergy.Fragments.FragCommunity;
import com.example.myallergy.Fragments.FragHome;
import com.example.myallergy.Fragments.FragCategory;
import com.example.myallergy.Fragments.FragSetting;
import com.example.myallergy.R;
import com.nhn.android.naverlogin.OAuthLogin;

public class MainActivity extends AppCompatActivity {
    OAuthLogin mOAuthLogin;
    String token;

    private AllergyDAO allergyDAO;
    private UserDAO userDAO;
    private UserDataBase userDataBase;

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
            createHomeScreen(); //홈 화면 생성
        }
        else { //로그인이 되어있다면
            createHomeScreen(); //홈 화면 생성
        }
        initializeDB();
        setUserAllergyDatas();
    }

    //홈화면, 네비게이션 바 생성
    private void createHomeScreen () {
        //네비게이션 바 생성
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
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
                        fragMedicine = new FragCategory();
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
    private void addFragment(Fragment frag) {
        fm.beginTransaction().add(R.id.main_frame, frag).commit();
    }
    private void showFragment(Fragment frag) {
        if(frag != null) fm.beginTransaction().show(frag).commit();
    }
    private void hideFragment(Fragment... frag) {
        for(Fragment tempFrag : frag) {
            if (tempFrag != null) fm.beginTransaction().hide(tempFrag).commit();
        }
    }

    //database, dao 초기화
    private void initializeDB() {
        userDataBase = UserDataBase.getInstance(getApplicationContext());
        userDAO = userDataBase.getUserDAO();
        allergyDAO = userDataBase.getAllergyDAO();
    }

    //알러지 정보, 닉네임을 db에서 가져와서 userprofile 설정
    private void setUserAllergyDatas () {
        new Thread() {
            public void run() {
                UserProfile.userAllergyDatas = allergyDAO.getAllergyList();
                if (userDAO.getUser() != null) {
                    UserProfile.userName = userDAO.getUser().getUserName();
                }
            }
        }.start();
    }
}
