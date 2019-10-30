package com.example.myallergy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.myallergy.Activities.ProductSearchActivity;
import com.example.myallergy.R;


public class FragCategory extends Fragment {
    LinearLayout drink, simple, snack, frozen, noodle, milk;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        initializeView(view);
        setClickEvent();

        return view;
    }

    private void initializeView(View view) {
        drink = view.findViewById(R.id.category_drink);
        simple = view.findViewById(R.id.category_simple);
        snack = view.findViewById(R.id.category_snack);
        frozen = view.findViewById(R.id.category_frozen);
        noodle = view.findViewById(R.id.category_noodle);
        milk = view.findViewById(R.id.category_milk);
    }

    private void setClickEvent() {
        drink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProductSearchActivity.class);
                intent.putExtra("type", "category");
                intent.putExtra("category", "drink");
                startActivity(intent);
            }
        });
        simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProductSearchActivity.class);
                intent.putExtra("type", "category");
                intent.putExtra("category", "simple");
                startActivity(intent);
            }
        });
        snack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProductSearchActivity.class);
                intent.putExtra("type", "category");
                intent.putExtra("category", "snack");
                startActivity(intent);
            }
        });
        frozen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProductSearchActivity.class);
                intent.putExtra("type", "category");
                intent.putExtra("category", "frozen");
                startActivity(intent);
            }
        });
        noodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProductSearchActivity.class);
                intent.putExtra("type", "category");
                intent.putExtra("category", "noodle");
                startActivity(intent);
            }
        });
        milk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProductSearchActivity.class);
                intent.putExtra("type", "category");
                intent.putExtra("category", "milk");
                startActivity(intent);
            }
        });
    }

}
