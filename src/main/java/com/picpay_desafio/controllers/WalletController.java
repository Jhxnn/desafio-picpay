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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.picpay_desafio.dtos.WalletDto;
import com.picpay_desafio.models.Wallet;
import com.picpay_desafio.services.WalletService;

@RestController
@RequestMapping("/wallet")
public class WalletController {

	@Autowired
	WalletService walletService;

	@GetMapping
	public ResponseEntity<List<Wallet>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(walletService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Wallet> findById(@PathVariable(name = "id")UUID id){
		return ResponseEntity.status(HttpStatus.OK).body(walletService.findById(id));
	}

	@PostMapping
	public ResponseEntity<Wallet> createWallet(@RequestBody WalletDto walletDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(walletService.createWallet(walletDto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Wallet> updateWallet(@PathVariable(name = "id")UUID id,
			@RequestBody WalletDto walletDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(walletService.updateWallet(id, walletDto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Wallet> deleteWallet(@PathVariable(name = "id")UUID id){
		walletService.deleteWallet(id);
		return ResponseEntity.noContent().build();
	}
}
