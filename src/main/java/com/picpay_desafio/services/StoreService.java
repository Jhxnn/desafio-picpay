package com.picpay_desafio.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpay_desafio.dtos.StoreDto;
import com.picpay_desafio.models.Store;
import com.picpay_desafio.repositories.UserRepository;
import com.picpay_desafio.repositories.StoreRepository;

@Service
public class StoreService {

	
	@Autowired
	StoreRepository storeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public List<Store> findAll(){
		return storeRepository.findAll();
	}
	
	public Store findById(UUID id) {
		return storeRepository.findById(id).orElseThrow(()-> new RuntimeException("cannot be found"));
	}
	
	public Store createStore(StoreDto storeDto) {
		var store = new Store();
		BeanUtils.copyProperties(storeDto, store);
		var owner = userRepository.findById(storeDto.owner()).orElseThrow(()-> new RuntimeException("cannot be found"));
		store.setOwner(owner);
		storeRepository.save(store);
		return store;
	}
	
	public Store updateStore(UUID id, StoreDto storeDto) {
		var store = findById(id);
		BeanUtils.copyProperties(storeDto, store);
		var owner = userRepository.findById(storeDto.owner()).orElseThrow(()-> new RuntimeException("cannot be found"));
		store.setOwner(owner);
		storeRepository.save(store);
		return store;
	}
	
	public void deleteStore(UUID id) {
		var store = findById(id);
		storeRepository.delete(store);
	}
}
