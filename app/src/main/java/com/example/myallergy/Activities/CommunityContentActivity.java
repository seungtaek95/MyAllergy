package com.example.myallergy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.myallergy.R;
import com.example.myallergy.Retrofit2.PostVO;

public class CommunityContentActivity extends AppCompatActivity {

    private TextView title, writer, date, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_content);

        initializeView();
        setTextView();
    }

    private void initializeView() {
        title = findViewById(R.id.item_detail_title);
        writer = findViewById(R.id.item_detail_writer);
        date = findViewById(R.id.item_detail_date);
        content = findViewById(R.id.item_detail_content);
    }

    private void setTextView() {
        Intent intent = getIntent();
        PostVO post = (PostVO) intent.getSerializableExtra("community");

        title.setText(post.getTitle());
        writer.setText(post.getWriter());
        date.setText(post.getDate());
        content.setText(post.getContent());
    }

}
