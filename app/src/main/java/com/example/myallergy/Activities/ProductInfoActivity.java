package com.example.myallergy.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myallergy.DataBase.Allergy;
import com.example.myallergy.DataBase.UserProfile;
import com.example.myallergy.R;
import com.example.myallergy.Retrofit2.ProductVO;


public class ProductInfoActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView alertAllergy, alertText, pname, prdkind, allergy, rawmtrl, nutrition, seller;

    StringBuffer alertMessage;
    private String productAllergy;
    boolean isContained;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_info_window);

        //view 초기화, 텍스트뷰 초기화
        initializeView();
        setTextView();
        alertMyAllergy();
    }

    private void initializeView() {
        imageView = findViewById(R.id.product_image);
        alertMessage = new StringBuffer();
        alertAllergy = findViewById(R.id.alert_my_allergy_allergy);
        alertText = findViewById(R.id.alert_my_allergy_text);
        pname = findViewById(R.id.product_pname);
        prdkind = findViewById(R.id.product_prdkind);
        allergy = findViewById(R.id.product_allergy);
        rawmtrl = findViewById(R.id.product_rawmtrl);
        nutrition = findViewById(R.id.product_nutrition);
        seller = findViewById(R.id.product_seller);
    }

    private void setTextView() {
        //인텐트로 받아온 값들로 텍스트뷰 설정
        Intent intent = getIntent();
        ProductVO product = (ProductVO) intent.getSerializableExtra("product");

        setImageView(product.getImgurl1());
        pname.setText(product.getPname());
        prdkind.setText(product.getPrdkind());
        allergy.setText(product.getAllergy());
        productAllergy = product.getAllergy();
        rawmtrl.setText(product.getRawmtrl());
        nutrition.setText(product.getNutrition());
        seller.setText(product.getSeller());
    }

    private void setImageView(String url) {
        Glide.with(this).load(url).into(imageView);
    }

    private void alertMyAllergy() {
        compareAllergy();
        setAlertTextView();
    }

    private void compareAllergy() {
        for (Allergy allergy : UserProfile.userAllergyDatas) {
            createAlertMessage(allergy.getAllergyName(), productAllergy);
        }
    }

    private void createAlertMessage(String myAllergy, String productAllergy) {
        if (productAllergy.contains(myAllergy)) {
            alertMessage.append("'" + myAllergy + "' ");
            isContained = true;
        }
    }

    private void setAlertTextView() {
        if (isContained) {
            alertAllergy.setText(alertMessage);
            alertAllergy.setTextSize(30);
            alertText.setTextSize(30);
        }
    }
}
