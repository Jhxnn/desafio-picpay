package com.picpay_desafio.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.picpay_desafio.models.Store;
import java.util.List;


public interface StoreRepository extends JpaRepository<Store, UUID>{

	Store findByCnpj(String cnpj);
}
