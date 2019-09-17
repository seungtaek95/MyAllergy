package com.example.myallergy;

import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //네비게이션 바 생성
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        //bottomNavigationView.setOnNavigationItemSelectedListener(navigationListener);
        //네비게이션 바 고정
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
    }
}
