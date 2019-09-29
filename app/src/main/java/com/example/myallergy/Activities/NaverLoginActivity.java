package com.example.myallergy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.myallergy.R;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import static com.nhn.android.naverlogin.OAuthLogin.mOAuthLoginHandler;

public class NaverLoginActivity extends AppCompatActivity {
    public static OAuthLogin mOAuthLoginModule;
    OAuthLoginButton mOAuthLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_login);

        //네이버로 로그인 모듈 초기화
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(this, "32ZtKDUrZ5z_TFOSBnzY", "zVKwrsfsvc", "clientName");
        //네이버로 로그인 실행
        mOAuthLoginButton=(OAuthLoginButton)findViewById(R.id.button_naverlogin);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
    }

    @Override
    public void onResume(){
        super.onResume();
        //네이버 로그인 사용자 인스턴스 가져오기
        OAuthLogin mOAuthLogin = OAuthLogin.getInstance();
        String token = mOAuthLogin.getAccessToken(getApplicationContext());

        if(token != null) { //로그인 된 상태라면
            Intent intent = new Intent(this, AllergySelectActivity.class);
            startActivity(intent);//알러지 선택 activity 실행
            finish();
        }
    }

    //뒤로가기 누르면 종료
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finishAffinity();
//        System.runFinalization();
//        System.exit(0);
    }
}
