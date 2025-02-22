package com.picpay_desafio.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private UUID paymentId;
	
	private double value;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id",name = "payer_wallet_id")
	private Wallet payerWallet;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id",name = "receiver_wallet_id")
	private Wallet receiverWallet;
	
	private String description;

	public UUID getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(UUID paymentId) {
		this.paymentId = paymentId;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	

	public Wallet getPayerWallet() {
		return payerWallet;
	}

	public void setPayerWallet(Wallet payerWallet) {
		this.payerWallet = payerWallet;
	}

	public Wallet getReceiverWallet() {
		return receiverWallet;
	}

	public void setReceiverWallet(Wallet receiverWallet) {
		this.receiverWallet = receiverWallet;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
