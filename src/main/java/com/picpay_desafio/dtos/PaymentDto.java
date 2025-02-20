package com.picpay_desafio.dtos;

import java.util.UUID;

public record PaymentDto(double value, UUID walletId, String description) {

}
