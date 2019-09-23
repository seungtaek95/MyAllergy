package com.example.myallergy.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myallergy.R;

import java.util.ArrayList;
import java.util.List;

public class FragCommunity extends Fragment {

    private LinearLayout mConatiner;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_community, container, false);
        mConatiner = view.findViewById(R.id.container_post);
        mConatiner .removeAllViews();

        List<Post> posts = getPosts();
        for (Post post : posts) {
            LinearLayout itemView = (LinearLayout) LinearLayout.inflate(container.getContext(), R.layout.item_post, null);

            TextView subject = itemView.findViewById(R.id.tv_subject);
            TextView author = itemView.findViewById(R.id.tv_author);

            subject.setText(post.subject);
            author.setText(post.author);

            mConatiner.addView(itemView);
        }

        return view;
    }

    private List<Post> getPosts() {
        ArrayList<Post> posts = new ArrayList<>();

        posts.add(new Post("안녕안녕", "안녕하세여ㅛ", "재경"));
        posts.add(new Post("안녕안녕", "안녕하세여ㅛ", "재경"));
        posts.add(new Post("안녕안녕", "안녕하세여ㅛ", "재경"));
        posts.add(new Post("안녕안녕", "안녕하세여ㅛ", "재경"));
        posts.add(new Post("안녕안녕", "안녕하세여ㅛ", "재경"));
        posts.add(new Post("안녕안녕", "안녕하세여ㅛ", "재경"));

        return posts;
    }
}

class Post {
    public Post(String subject, String content, String author) {
        this.subject = subject;
        this.content = content;
        this.author = author;
    }

    String subject;
    String content;
    String author;
}