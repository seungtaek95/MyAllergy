package com.example.myallergy.Retrofit2;

import java.io.Serializable;

public class MedicineVO implements Serializable {
	private String category;
	private String effect;
	private String validate;
	private String capacity;
	private String company;
	private String mname;
	private String ingredient;
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String pname) {
		this.mname = pname;
	}
	public String getIngredient() {
		return ingredient;
	}
	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}
}
