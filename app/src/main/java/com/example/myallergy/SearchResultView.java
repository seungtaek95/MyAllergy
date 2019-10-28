package com.example.myallergy;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.myallergy.Activities.MedicineInfoActivity;
import com.example.myallergy.Activities.ProductInfoActivity;
import com.example.myallergy.Retrofit2.MedicineVO;
import com.example.myallergy.Retrofit2.ProductVO;

public class SearchResultView {

    public TextView createResultTextProduct(final Context context, final ProductVO product) {
        TextView resultText = new TextView(context);
        resultText.setPadding(30, 30, 30, 30);
        resultText.setTextSize(30);
        resultText.setMaxLines(1);
        //뒷부분 ... 으로 표시
        resultText.setEllipsize(TextUtils.TruncateAt.END);
        resultText.setText(product.getPname());
        //클릭 시 상세정보 화면 전환
        resultText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductInfoActivity.class);
                intent.putExtra("product", product);
                context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        return resultText;
    }

    //검색 결과 textview 생성
    public TextView createResultMedicine (final Context context, final MedicineVO medicine) {
        TextView resultText = new TextView(context);
        resultText.setPadding(30, 30, 30, 30);
        resultText.setTextSize(30);
        resultText.setMaxLines(1);
        //뒷부분 ... 으로 표시
        resultText.setEllipsize(TextUtils.TruncateAt.END);
        resultText.setText(medicine.getMname());
        //클릭 시 상세정보 화면 전환
        resultText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MedicineInfoActivity.class);
                intent.putExtra("medicine", medicine);
                context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        return resultText;
    }

    public TextView createNothingFoundResult (Context context) {
        TextView resultText = new TextView(context);
        resultText.setGravity(Gravity.CENTER);
        resultText.setText("검색 결과가 없습니다");
        resultText.setTextSize(30);

        return resultText;
    }
}
