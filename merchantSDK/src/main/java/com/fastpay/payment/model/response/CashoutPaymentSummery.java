package com.fastpay.payment.model.response;

import java.io.Serializable;

public class CashoutPaymentSummery implements Serializable {
	private CashoutSummary cashoutSummary;

	public void setSummary(CashoutSummary cashoutSummary){
		this.cashoutSummary = cashoutSummary;
	}

	public CashoutSummary getSummary(){
		return cashoutSummary;
	}
}
