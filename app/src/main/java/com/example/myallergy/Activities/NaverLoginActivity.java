package com.example.myallergy.Activities;

import android.os.Bundle;
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
            finish();
        }
    }
}
