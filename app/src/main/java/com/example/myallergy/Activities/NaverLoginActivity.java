
package com.example.myallergy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.myallergy.DataBase.AllergyDAO;
import com.example.myallergy.DataBase.UserDAO;
import com.example.myallergy.DataBase.UserDataBase;
import com.example.myallergy.R;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import static com.nhn.android.naverlogin.OAuthLogin.mOAuthLoginHandler;

public class NaverLoginActivity extends AppCompatActivity {
    public static OAuthLogin mOAuthLoginModule;
    OAuthLoginButton mOAuthLoginButton;
    private OAuthLoginHandler mOAuthLoginHandler;

    private UserDataBase db;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_login);

        //네이버로 로그인 모듈 초기화
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(this, "32ZtKDUrZ5z_TFOSBnzY", "zVKwrsfsvc", "clientName");
        mOAuthLoginHandler = new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {
                if (success) {
                    startUserSetActivity();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "로그인에 실패했습니다", Toast.LENGTH_LONG).show();
                }
            }
        };
        //네이버로 로그인 실행
        mOAuthLoginButton=findViewById(R.id.button_naverlogin);
        mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
    }

    private void startUserSetActivity() {
        initializeDB();

        //db에 저장된 사용자 정보가 없으면
        new Thread() {
            public void run() {
                if (userDAO.getUser() == null) {
                    Intent intent = new Intent(getApplicationContext(), CreateUserNameActivity.class);
                    startActivity(intent);//유저 이름 생성 activity 실행
                    intent = new Intent(getApplicationContext(), AllergySelectActivity.class);
                    startActivity(intent);//알러지 선택 activity 실행
                }
            }
        }.start();
    }

    private void initializeDB() {
        db = UserDataBase.getInstance(getApplicationContext());
        userDAO = db.getUserDAO();
    }

    //뒤로가기 누르면 종료
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}

