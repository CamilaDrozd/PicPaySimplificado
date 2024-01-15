package com.picpaySimplificado.services;

import com.picpaySimplificado.DTOs.TransactionDTO;
import com.picpaySimplificado.domain.user.User;
import com.picpaySimplificado.domain.user.UserType;
import com.picpaySimplificado.repositories.TransactionalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
class TransactionalServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private TransactionalRepository repository;

    @Mock
    private AuhtorizationService auhtorizationService;

    @Mock
    private NotificationService notificationService;

    @Autowired
    @InjectMocks
    private TransactionalService transactionalService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should create transaction successfuly when everything is OK")
    void createTransactionCase1() throws Exception {
        User sender = new User(1L,"Maria", "Souza", "99999999901", "MAria@gmail.com", "12345", new BigDecimal(10), UserType.COMMON);
        User receiver = new User(2L,"João", "Dias", "99999999902", "João@gmail.com", "12345", new BigDecimal(10), UserType.COMMON);

        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);

        when(auhtorizationService.authorizedTransaction(any(),any())).thenReturn(true);

        TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1l, 2l);
        transactionalService.createTransaction(request);

        verify(repository, times(1)).save(any());

        sender.setBalance(new BigDecimal(0));
        verify(userService, times(1)).saveUser(sender);


        receiver.setBalance(new BigDecimal(20));
        verify(userService, times(1)).saveUser(receiver);

        verify(notificationService, times(1)).sendNotification(sender,"Transação realizada com sucesso");
        verify(notificationService, times(1)).sendNotification(receiver,"Transação recebida com sucesso");
    }

    @Test
    @DisplayName("Should throw Exception when Transaction is not allowed")
    void createTransactionCase2() {
    }
}