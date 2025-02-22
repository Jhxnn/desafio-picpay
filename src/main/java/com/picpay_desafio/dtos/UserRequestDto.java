package com.picpay_desafio.dtos;

import com.picpay_desafio.models.enums.UserRole;

public record UserRequestDto(String name, String email, String password, String cpf, UserRole role) {

}