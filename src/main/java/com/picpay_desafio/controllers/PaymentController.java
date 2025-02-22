package com.picpay_desafio.controllers;

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

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	PaymentService paymentService;

	@GetMapping
	public ResponseEntity<List<Payment>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(paymentService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Payment> findById(@PathVariable(name = "id")UUID id){
		return ResponseEntity.status(HttpStatus.OK).body(paymentService.findById(id));
	}

	@PostMapping
	public ResponseEntity<Payment> createPayment(@RequestBody PaymentDto paymentDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.doPayment(paymentDto));
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Payment> deletePayment(@PathVariable(name = "id")UUID id){
		paymentService.deletePayment(id);
		return ResponseEntity.noContent().build();
	}
}
