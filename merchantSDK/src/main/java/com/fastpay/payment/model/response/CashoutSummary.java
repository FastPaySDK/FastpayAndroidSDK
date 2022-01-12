package com.fastpay.payment.model.response;

import java.io.Serializable;

public class CashoutSummary implements Serializable {
	private String amount;
	private CashoutRecipient cashoutRecipient;
	private String invoiceId;
	private String transactionType;
	private String orderId;

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setRecipient(CashoutRecipient cashoutRecipient){
		this.cashoutRecipient = cashoutRecipient;
	}

	public CashoutRecipient getRecipient(){
		return cashoutRecipient;
	}

	public void setInvoiceId(String invoiceId){
		this.invoiceId = invoiceId;
	}

	public String getInvoiceId(){
		return invoiceId;
	}

	public void setTransactionType(String transactionType){
		this.transactionType = transactionType;
	}

	public String getTransactionType(){
		return transactionType;
	}

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}
}
