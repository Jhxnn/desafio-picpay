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

import com.picpay_desafio.dtos.StoreDto;
import com.picpay_desafio.models.Store;
import com.picpay_desafio.services.StoreService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/store")
public class StoreController {

	@Autowired
	StoreService storeService;
	
	
	@Operation(description = "Lista todas as lojas")
	@GetMapping
	public ResponseEntity<List<Store>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(storeService.findAll());
	}

	@Operation(description="Loja pelo id")
	@GetMapping("/{id}")
	public ResponseEntity<Store> findById(@PathVariable(name = "id")UUID id){
		return ResponseEntity.status(HttpStatus.OK).body(storeService.findById(id));
	}

	@Operation(description="Cria uma loja")
	@PostMapping
	public ResponseEntity<Store> createStore(@RequestBody StoreDto storeDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(storeService.createStore(storeDto));
	}
	@Operation(description="Buscar Loja pelo cnpj")
	@GetMapping("/cnpj/{cnpj}")
	public ResponseEntity<Store> findByCnpj(@PathVariable(name = "cnpj")String cnpj){
		return ResponseEntity.status(HttpStatus.OK).body(storeService.findByCnpj(cnpj));
	}
	@Operation(description="Atualiza uma loja")
	@PutMapping("/{id}")
	public ResponseEntity<Store> updateStore(@PathVariable(name = "id")UUID id,
			@RequestBody StoreDto storeDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(storeService.updateStore(id, storeDto));
	}
	@Operation(description="Deleta uma loja")
	@DeleteMapping("/{id}")
	public ResponseEntity<Store> deleteStore(@PathVariable(name = "id")UUID id){
		storeService.deleteStore(id);
		return ResponseEntity.noContent().build();
	}
}