package com.picpay_desafio.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.picpay_desafio.dtos.PaymentDto;
import com.picpay_desafio.models.Payment;
import com.picpay_desafio.services.PaymentService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	PaymentService paymentService;


	@Operation(description="Lista todos os pagamentos")
	@GetMapping
	public ResponseEntity<List<Payment>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(paymentService.findAll());
	}

	@Operation(description="Lista pagamento pelo id")
	@GetMapping("/{id}")
	public ResponseEntity<Payment> findById(@PathVariable(name = "id")UUID id){
		return ResponseEntity.status(HttpStatus.OK).body(paymentService.findById(id));
	}
	
	@Operation(description="Lista todos os pagamentos de uma data inicial ate uma data final")
	@GetMapping("/between/{startDate}/{endDate}")
	public ResponseEntity<List<Payment>> findByBetweenDate(@PathVariable(name = "startDate")LocalDateTime startDate,
			@PathVariable(name = "endDate")LocalDateTime endDate){
		return ResponseEntity.status(HttpStatus.OK).body(paymentService.findByBetweenDate(startDate, endDate));
	}

	@Operation(description="Realiza um pagamento")
	@PostMapping
	public ResponseEntity<Payment> doPayment(@RequestBody PaymentDto paymentDto){
		var payment = paymentService.doPayment(paymentDto);
		if(payment == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(payment);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(payment);
	}

	@Operation(description="Deleta um pagamento")
	@DeleteMapping("/{id}")
	public ResponseEntity<Payment> deletePayment(@PathVariable(name = "id")UUID id){
		paymentService.deletePayment(id);
		return ResponseEntity.noContent().build();
	}
}
