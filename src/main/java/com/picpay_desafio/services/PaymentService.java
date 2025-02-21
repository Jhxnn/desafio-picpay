package com.picpay_desafio.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpay_desafio.dtos.PaymentDto;
import com.picpay_desafio.models.Payment;
import com.picpay_desafio.repositories.PaymentRepository;

@Service
public class PaymentService {

	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	WalletService walletService;
	
	public List<Payment> findAll(){
		return paymentRepository.findAll();
	}
	
	public Payment findById(UUID id) {
		return paymentRepository.findById(id).orElseThrow(()-> new RuntimeException("cannot be found"));
	}
	
	public Payment createPayment(PaymentDto paymentDto) {
		var payment = new Payment();
		BeanUtils.copyProperties(paymentDto, payment);
		var wallet = walletService.findById(paymentDto.walletId());
		payment.setWallet(wallet);
		return paymentRepository.save(payment);
		
	}
	
	public Payment updatePayment(UUID id, PaymentDto paymentDto) {
		var payment = findById(id);
		BeanUtils.copyProperties(paymentDto, payment);
		var wallet = walletService.findById(paymentDto.walletId());
		payment.setWallet(wallet);
		return paymentRepository.save(payment);
		
		
	}
	
	public void deletePayment(UUID id) {
		var payment = findById(id);
		paymentRepository.delete(payment);
	}
}
