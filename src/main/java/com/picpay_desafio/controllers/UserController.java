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

import com.picpay_desafio.dtos.AuthDto;
import com.picpay_desafio.dtos.UserRequestDto;
import com.picpay_desafio.dtos.UserResponseDto;
import com.picpay_desafio.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	
	@Autowired
	UserService userService;
	
	
	@GetMapping
	public ResponseEntity<List<UserResponseDto>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
	}
	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDto> findById(@PathVariable(name = "id")UUID id){
		return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequestDto));
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody AuthDto authDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.returnToken(authDto));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserResponseDto> updateUser(@PathVariable(name = "id")UUID id,
			@RequestBody UserRequestDto userRequesDto){
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.updateUser(id, userRequesDto));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<UserResponseDto> deleteUser(@PathVariable(name = "id")UUID id){
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
	
}
