package com.picpay_desafio.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpay_desafio.dtos.PaymentDto;
import com.picpay_desafio.models.Payment;
import com.picpay_desafio.models.enums.UserRole;
import com.picpay_desafio.repositories.PaymentRepository;

@Service
public class PaymentService {

	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	WalletService walletService;
	
	@Autowired
	EmailService emailService;
	
	public List<Payment> findAll(){
		return paymentRepository.findAll();
	}
	
	public Payment findById(UUID id) {
		return paymentRepository.findById(id).orElseThrow(()-> new RuntimeException("cannot be found"));
	}
	
	public Payment doPayment(PaymentDto paymentDto) {
		var payerWallet = walletService.findById(paymentDto.payerWalletId());
		var receiverWallet = walletService.findById(paymentDto.receiverWalletId());
		var payment = new Payment();
		if(payerWallet.getAmount() < paymentDto.value()) {
			return null;
		}
		else if(payerWallet.getUsers().getRole() == UserRole.STORE_OWNER) {
			return null;
		}
		
		BeanUtils.copyProperties(paymentDto, payment);
		payerWallet.setAmount(payerWallet.getAmount() - paymentDto.value());
		payerWallet.setAmount(payerWallet.getAmount() + paymentDto.value());
		payment.setReceiverWallet(receiverWallet);
		payment.setPayerWallet(payerWallet);
		emailService.enviarEmailTexto(payerWallet.getUsers().getEmail(), "Pagamento efetuado", "Um pagamento foi efetuado no valor de " + paymentDto.value() +  " R$ para: " + receiverWallet.getUsers().getName());
		emailService.enviarEmailTexto(receiverWallet.getUsers().getEmail(), "Pagamento recebido", "Um pagamento foi recebido no valor de " + paymentDto.value() +  " R$ de: " + payerWallet.getUsers().getName());
		return paymentRepository.save(payment);

	}

	
	public void deletePayment(UUID id) {
		var payment = findById(id);
		paymentRepository.delete(payment);
	}
}
