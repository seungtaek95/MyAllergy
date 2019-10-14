package com.example.myallergy.Retrofit2;

import java.io.Serializable;

public class ProductVO implements Serializable {
    private String imgurl1;
    private String pname;
    private String prdkind;
    private String allergy;
    private String rawmtrl;
    private String nutrition;
    private String seller;
    private String barcode;

    public String getImgurl1() {
        return imgurl1;
    }
    public void setImgurl1(String imgurl1) {
        this.imgurl1 = imgurl1;
    }
    public String getPname() {
        return pname;
    }
    public void setPname(String pname) {
        this.pname = pname;
    }
    public String getRawmtrl() {
        return rawmtrl;
    }
    public void setRawmtrl(String rawmtrl) {
        this.rawmtrl = rawmtrl;
    }
    public String getAllergy() {
        return allergy;
    }
    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }
    public String getNutrition() {
        return nutrition;
    }
    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }
    public String getSeller() {
        return seller;
    }
    public void setSeller(String seller) {
        this.seller = seller;
    }
    public String getBarcode() {
        return barcode;
    }
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    public String getPrdkind() {
        return prdkind;
    }
    public void setPrdkind(String prdkind) {
        this.prdkind = prdkind;
    }

}