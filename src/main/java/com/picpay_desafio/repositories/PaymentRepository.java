package com.picpay_desafio.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.picpay_desafio.models.Payment;

public interface PaymentRepository extends JpaRepository<Payment, UUID>{

}
