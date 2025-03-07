package com.picpay_desafio.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpay_desafio.dtos.PaymentDto;
import com.picpay_desafio.models.Payment;
import com.picpay_desafio.models.enums.UserRole;
import com.picpay_desafio.repositories.PaymentRepository;
import com.picpay_desafio.repositories.WalletRepository;

@Service
public class PaymentService {

	
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	WalletService walletService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	WalletRepository walletRepository;
	
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
		receiverWallet.setAmount(receiverWallet.getAmount() + paymentDto.value());
		payment.setReceiverWallet(receiverWallet);
		payment.setPayerWallet(payerWallet);
		emailService.enviarEmailTexto(payerWallet.getUsers().getEmail(), "Pagamento efetuado", "Um pagamento foi efetuado no valor de " + paymentDto.value() +  " R$ para: " + receiverWallet.getUsers().getName());
		emailService.enviarEmailTexto(receiverWallet.getUsers().getEmail(), "Pagamento recebido", "Um pagamento foi recebido no valor de " + paymentDto.value() +  " R$ de: " + payerWallet.getUsers().getName());
		walletRepository.save(payerWallet);
		walletRepository.save(receiverWallet);
		return paymentRepository.save(payment);

	}
	
	public List<Payment> findByBetweenDate(LocalDateTime starDate, LocalDateTime endDate){
		return paymentRepository.findByPaymentDateBetween(starDate, endDate);
	}
	

	
	public void deletePayment(UUID id) {
		var payment = findById(id);
		paymentRepository.delete(payment);
	}
}
