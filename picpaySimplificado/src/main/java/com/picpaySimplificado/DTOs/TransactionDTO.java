package com.picpaySimplificado.DTOs;

import java.math.BigDecimal;
import java.util.function.BiConsumer;

public record TransactionDTO(BigDecimal value, Long senderId, Long receiverId) {
}
