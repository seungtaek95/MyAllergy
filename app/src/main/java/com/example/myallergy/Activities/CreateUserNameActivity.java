package com.example.myallergy.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myallergy.DataBase.AllergyDAO;
import com.example.myallergy.DataBase.MedicineDAO;
import com.example.myallergy.DataBase.User;
import com.example.myallergy.DataBase.UserDAO;
import com.example.myallergy.DataBase.UserDataBase;
import com.example.myallergy.DataBase.UserProfile;
import com.example.myallergy.R;
import com.example.myallergy.Retrofit2.ProductVO;
import com.example.myallergy.Retrofit2.WebEndPoint;
import com.nhn.android.naverlogin.OAuthLogin;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateUserNameActivity extends AppCompatActivity {
    EditText eTextUserName;
    Button btnSubmit;
    UserDataBase db;
    UserDAO userDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_name);

        initializeView();
    }

    private void initializeView() {
        eTextUserName = findViewById(R.id.eText_create_user_name);
        btnSubmit = findViewById(R.id.btn_submit_user_name);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard(getCurrentFocus());
                if ((eTextUserName.getText().toString()).equals("")) {
                    Toast.makeText(getApplicationContext(), "닉네임을 입력하세요", Toast.LENGTH_LONG).show();
                } else {
                    checkNickname(eTextUserName.getText().toString());
                }
            }
        });
    }

    private WebEndPoint getEndPoint() {
        WebEndPoint endPoint = new Retrofit.Builder()
                .baseUrl(WebEndPoint.URL)
                .build()
                .create(WebEndPoint.class);
        return endPoint;
    }

    public void checkNickname(String nickname) {
        WebEndPoint endPoint = getEndPoint();

        endPoint.checkNickname(nickname).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 400) {
                    Toast.makeText(getApplicationContext(), "중복된 닉네임입니다", Toast.LENGTH_LONG).show();
                } else if (response.code() == 200) {
                    initializeDB();
                    setUserName();
                    finish();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void initializeDB() {
        db = UserDataBase.getInstance(getApplicationContext());
        userDAO = db.getUserDAO();
    }

    private void setUserName() {
        new Thread() {
            public void run() {
                User user = new User();
                user.setUserName(eTextUserName.getText().toString());
                userDAO.insert(user);
            }
        }.start();
        UserProfile.userName = eTextUserName.getText().toString();
    }

    //키보드 숨기기
    private void hideKeyBoard (View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //뒤로가기 누르면 종료
    @Override
    public void onBackPressed() {
        new Thread() {
            public void run() {
                deleteUserInfo();
            }
        }.start();
        finishAffinity();
    }

    public void deleteUserInfo() {
        //네이버 로그인 사용자 정보 삭제
        OAuthLogin mOAuthLogin = OAuthLogin.getInstance();
        mOAuthLogin.logoutAndDeleteToken(getApplicationContext());

        UserProfile.userName = new String();
        UserProfile.userAllergyDatas = new ArrayList<>();

        //db 데이터 초기화
        UserDataBase db = UserDataBase.getInstance(getApplicationContext());
        AllergyDAO allergyDAO = db.getAllergyDAO();
        allergyDAO.deleteAllergy();
        MedicineDAO medicineDAO = db.getMedicineDAO();
        medicineDAO.deleteAllMedicine();
        UserDAO userDAO = db.getUserDAO();
        userDAO.deleteUser();
    }
}
