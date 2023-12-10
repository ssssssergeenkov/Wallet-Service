package com.ivan.wallet.service;

import com.ivan.wallet.dao.impl.TransactionsDaoImpl;
import com.ivan.wallet.domain.Transaction;
import com.ivan.wallet.service.impl.PlayerWalletServiceImpl;
import com.ivan.wallet.service.impl.TransactionWalletServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static com.ivan.wallet.domain.types.IdentifierType.FAIL;
import static com.ivan.wallet.domain.types.IdentifierType.SUCCESS;
import static com.ivan.wallet.domain.types.TransactionType.CREDIT;
import static com.ivan.wallet.domain.types.TransactionType.DEBIT;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionWalletServiceImplTest {

    @Mock
    private TransactionsDaoImpl transactionsDaoImpl;
    @Mock
    private PlayerWalletServiceImpl playerWalletServiceImpl;

    @InjectMocks
    private TransactionWalletServiceImpl transactionWalletServiceImpl;

    @Test
    void debit_Successful() {
        String username = "username";
        BigDecimal amount = BigDecimal.valueOf(10);
        when(playerWalletServiceImpl.currentPlayerBalance(username)).thenReturn(BigDecimal.valueOf(200));

        transactionWalletServiceImpl.debit(username, amount);
        verify(playerWalletServiceImpl, times(1)).updateBalance(username, BigDecimal.valueOf(190));
    }

    @Test
    void debit_Failed_Because_The_Player_Does_Not_Have_Enough_Funds() {
        String username = "username";
        BigDecimal amount = BigDecimal.valueOf(110);
        when(playerWalletServiceImpl.currentPlayerBalance(username)).thenReturn(BigDecimal.valueOf(100));

        transactionWalletServiceImpl.debit(username, amount);

        verify(playerWalletServiceImpl, never()).updateBalance(any(String.class), any(BigDecimal.class));
    }

    @Test
    void credit_Successful() {
        String username = "testUser";
        BigDecimal amount = BigDecimal.valueOf(100);
        when(playerWalletServiceImpl.currentPlayerBalance(username)).thenReturn(BigDecimal.valueOf(200));

        transactionWalletServiceImpl.credit(username, amount);

        verify(playerWalletServiceImpl, times(1)).updateBalance(username, BigDecimal.valueOf(300));
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    void showTransactionHistory(String username, List<Transaction> transactionList) {
        when(transactionsDaoImpl.findByName(username)).thenReturn(transactionList);

        transactionWalletServiceImpl.showTransactionHistory(username);

        verify(transactionsDaoImpl).findByName(username);
    }

    static Stream<Arguments> getArguments() {
        return Stream.of(
                Arguments.of("Ivan_Pupkin", Collections.singletonList(
                        Transaction.builder()
                                .id(new Random().nextInt(Integer.MAX_VALUE))
                                .playerName("Ivan_Pupkin")
                                .type(DEBIT)
                                .amount(BigDecimal.valueOf(new Random().nextInt(10000)))
                                .identifierType(SUCCESS)
                                .build())),

                Arguments.of("Ivan_Popkin", Arrays.asList(
                        Transaction.builder()
                                .id(new Random().nextInt(Integer.MAX_VALUE))
                                .playerName("Ivan_Popkin")
                                .type(CREDIT)
                                .amount(BigDecimal.valueOf(new Random().nextInt(10000)))
                                .identifierType(SUCCESS)
                                .build(),

                        Transaction.builder()
                                .id(new Random().nextInt(Integer.MAX_VALUE))
                                .playerName("Ivan_Popkin")
                                .type(CREDIT)
                                .amount(BigDecimal.valueOf(new Random().nextInt(10000)))
                                .identifierType(SUCCESS)
                                .build())),


                Arguments.of("Ivan_Zalupkin", Arrays.asList(
                        Transaction.builder()
                                .id(new Random().nextInt(Integer.MAX_VALUE))
                                .playerName("Ivan_Zalupkin")
                                .type(DEBIT)
                                .amount(BigDecimal.valueOf(new Random().nextInt(10000)))
                                .identifierType(FAIL)
                                .build(),

                        Transaction.builder()
                                .id(new Random().nextInt(Integer.MAX_VALUE))
                                .playerName("Ivan_Zalupkin")
                                .type(DEBIT)
                                .amount(BigDecimal.valueOf(new Random().nextInt(10000)))
                                .identifierType(SUCCESS)
                                .build(),

                        Transaction.builder()
                                .id(new Random().nextInt(Integer.MAX_VALUE))
                                .playerName("Ivan_Zalupkin")
                                .type(CREDIT)
                                .amount(BigDecimal.valueOf(new Random().nextInt(10000)))
                                .identifierType(SUCCESS)
                                .build()))
        );
    }
}