package com.picpay_desafio.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.picpay_desafio.models.User;
import com.picpay_desafio.models.Wallet;


public interface WalletRepository extends JpaRepository<Wallet, UUID>{
	
	Wallet findByUsers(User users);

}
