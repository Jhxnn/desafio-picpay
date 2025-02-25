package com.picpay_desafio.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpay_desafio.dtos.WalletDto;
import com.picpay_desafio.models.Wallet;
import com.picpay_desafio.repositories.UserRepository;
import com.picpay_desafio.repositories.WalletRepository;

@Service
public class WalletService {

	
	@Autowired
	WalletRepository walletRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public List<Wallet> findAll(){
		return walletRepository.findAll();
	}
	
	public Wallet findById(UUID id) {
		return walletRepository.findById(id).orElseThrow(()-> new RuntimeException("cannot be found"));
	}
	
	public Wallet createWallet(WalletDto walletDto) {
		var wallet = new Wallet();
		BeanUtils.copyProperties(walletDto, wallet);
		var user = userRepository.findById(walletDto.users()).orElseThrow(()-> new RuntimeException("cannot be found"));
		wallet.setUsers(user);
		walletRepository.save(wallet);
		return wallet;
	}
	
	public Wallet findByUser(UUID id) {
		var user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("cannot be found"));
		return walletRepository.findByUsers(user);
	}
	
	public Wallet updateWallet(UUID id, WalletDto walletDto) {
		var wallet = findById(id);
		BeanUtils.copyProperties(walletDto, wallet);
		var user = userRepository.findById(walletDto.users()).orElseThrow(()-> new RuntimeException("cannot be found"));
		wallet.setUsers(user);
		walletRepository.save(wallet);
		return wallet;
	}
	
	public void deleteWallet(UUID id) {
		var wallet = findById(id);
		walletRepository.delete(wallet);
	}
}