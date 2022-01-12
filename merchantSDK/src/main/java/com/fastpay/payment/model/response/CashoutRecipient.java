package com.fastpay.payment.model.response;

import java.io.Serializable;

public class CashoutRecipient implements Serializable {
	private String name;
	private String avatar;
	private String mobileNumber;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setAvatar(String avatar){
		this.avatar = avatar;
	}

	public String getAvatar(){
		return avatar;
	}

	public void setMobileNumber(String mobileNumber){
		this.mobileNumber = mobileNumber;
	}

	public String getMobileNumber(){
		return mobileNumber;
	}
}
