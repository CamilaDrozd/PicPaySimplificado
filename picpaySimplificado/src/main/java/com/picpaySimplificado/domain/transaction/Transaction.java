package com.picpaySimplificado.domain.transaction;

import com.picpaySimplificado.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transactions")
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    @ManyToOne //Uma transação só pode ter 1 Sender
    @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne //Uma transação só pode ter 1 Receiver
    @JoinColumn(name = "receiver_id")
    private User receiver;
    private LocalDateTime timestamp;



}
