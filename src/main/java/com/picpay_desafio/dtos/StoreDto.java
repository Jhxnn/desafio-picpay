package com.picpay_desafio.dtos;

import java.util.UUID;

public record StoreDto(UUID owner, String name, String cnpj) {

}
