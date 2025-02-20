package com.picpay_desafio.dtos;

import java.util.UUID;

public record UserRequestDto(UUID userId, String name, String email) {

}
