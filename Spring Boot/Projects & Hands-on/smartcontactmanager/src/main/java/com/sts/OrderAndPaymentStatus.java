package com.sts;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OrderAndPaymentStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String orderId;
	private String amount;
	private String receipt;
	private String status;
	@ManyToOne //one user many payments
	private User user;
	private String paymentId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	@Override
	public String toString() {
		return "OrderAndPaymentStatus [id=" + id + ", orderId=" + orderId + ", amount=" + amount + ", receipt="
				+ receipt + ", status=" + status + ", user=" + user + ", paymentId=" + paymentId + "]";
	}
	
	
}
