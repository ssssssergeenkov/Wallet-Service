//package com.ivan.wallet.service;
//
//import com.ivan.wallet.dao.impl.PlayersDaoImpl;
//import com.ivan.wallet.dao.impl.TransactionsDaoImpl;
//import com.ivan.wallet.domain.Player;
//import com.ivan.wallet.domain.Transaction;
//import com.ivan.wallet.service.impl.PlayerWalletServiceImpl;
//import com.ivan.wallet.service.impl.TransactionWalletServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.math.BigDecimal;
//import java.util.*;
//import java.util.stream.Stream;
//
//import static com.ivan.wallet.domain.types.IdentifierType.FAIL;
//import static com.ivan.wallet.domain.types.IdentifierType.SUCCESS;
//import static com.ivan.wallet.domain.types.TransactionType.CREDIT;
//import static com.ivan.wallet.domain.types.TransactionType.DEBIT;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class TransactionWalletServiceImplTest {
//
//    @Mock
//    private PlayersDaoImpl playersDaoImpl;
//    @Mock
//    private TransactionsDaoImpl transactionsDaoImpl;
//
//    @InjectMocks
//    private TransactionWalletServiceImpl transactionWalletServiceImpl;
//    @InjectMocks
//    private PlayerWalletServiceImpl playerWalletServiceImpl;
//
//    @Test
//    void debit_Successful() {
//        BigDecimal amount = BigDecimal.TEN;
//        String name = "username";
//        when(playerWalletServiceImpl.currentPlayerBalance(name)).thenReturn(BigDecimal.valueOf(200));
//
//        transactionWalletServiceImpl.debit(name, amount);
//        verify(playerWalletServiceImpl, times(1)).updateBalance(name, BigDecimal.valueOf(190));
//    }
//
//    @Test
//    void debit_Failed_Because_The_Player_Does_Not_Exist() {
//        Player player = getPlayer();
//
//        when(playersDaoImpl.findByName(player.getName())).thenReturn(Optional.empty());
//
//        transactionWalletServiceImpl.debit(player.getName(), BigDecimal.valueOf(50));
//
//        verify(playersDaoImpl, times(0)).updatePlayerBalance(player.getName(), player.getBalance());
//    }
//
//    @Test
//    void debit_Failed_Because_The_Player_Does_Not_Have_Enough_Funds() {
//        Player player = getPlayer();
//
//        BigDecimal debitAmount = BigDecimal.valueOf(110);
//
//        when(playersDaoImpl.findByName(player.getName())).thenReturn(Optional.of(player));
//
//        transactionWalletServiceImpl.debit(player.getName(), debitAmount);
//
//        verify(playersDaoImpl, times(0)).updatePlayerBalance(player.getName(), player.getBalance());
//    }
//
//    @Test
//    void credit_Successful() {
//        String username = "testUser";
//        BigDecimal initialBalance = new BigDecimal("100");
//        BigDecimal creditAmount = new BigDecimal("50");
//
//        transactionWalletServiceImpl.credit(username, initialBalance);
//
//        verify(playersDaoImpl, times(1)).updatePlayerBalance(username, initialBalance.add(creditAmount));
//    }
//
//    @Test
//    void credit_Failed_Because_The_Player_Does_Not_Exist() {
//        Player player = getPlayer();
//
//        when(playersDaoImpl.findByName(player.getName())).thenReturn(Optional.empty());
//
//        transactionWalletServiceImpl.credit(player.getName(), BigDecimal.valueOf(50));
//
//        verify(playersDaoImpl, times(0)).updatePlayerBalance(player.getName(), player.getBalance());
//    }
//
//
//    @ParameterizedTest
//    @MethodSource("getArguments")
//    void showTransactionHistory(String username, List<Transaction> transactionList) {
//        when(transactionsDaoImpl.findByName(username)).thenReturn(transactionList);
//
//        transactionWalletServiceImpl.showTransactionHistory(username);
//
//        verify(transactionsDaoImpl).findByName(username);
//    }
//
//    static Stream<Arguments> getArguments() {
//        return Stream.of(
//                Arguments.of("Ivan_Pupkin", Collections.singletonList(
//                        Transaction.builder()
//                                .id(new Random().nextInt(Integer.MAX_VALUE))
//                                .playerName("Ivan_Pupkin")
//                                .type(DEBIT)
//                                .amount(BigDecimal.valueOf(new Random().nextInt(10000)))
//                                .identifierType(SUCCESS)
//                                .build())),
//
//                Arguments.of("Ivan_Popkin", Arrays.asList(
//                        Transaction.builder()
//                                .id(new Random().nextInt(Integer.MAX_VALUE))
//                                .playerName("Ivan_Popkin")
//                                .type(CREDIT)
//                                .amount(BigDecimal.valueOf(new Random().nextInt(10000)))
//                                .identifierType(SUCCESS)
//                                .build(),
//
//                        Transaction.builder()
//                                .id(new Random().nextInt(Integer.MAX_VALUE))
//                                .playerName("Ivan_Popkin")
//                                .type(CREDIT)
//                                .amount(BigDecimal.valueOf(new Random().nextInt(10000)))
//                                .identifierType(SUCCESS)
//                                .build())),
//
//
//                Arguments.of("Ivan_Zalupkin", Arrays.asList(
//                        Transaction.builder()
//                                .id(new Random().nextInt(Integer.MAX_VALUE))
//                                .playerName("Ivan_Zalupkin")
//                                .type(DEBIT)
//                                .amount(BigDecimal.valueOf(new Random().nextInt(10000)))
//                                .identifierType(FAIL)
//                                .build(),
//
//                        Transaction.builder()
//                                .id(new Random().nextInt(Integer.MAX_VALUE))
//                                .playerName("Ivan_Zalupkin")
//                                .type(DEBIT)
//                                .amount(BigDecimal.valueOf(new Random().nextInt(10000)))
//                                .identifierType(SUCCESS)
//                                .build(),
//
//                        Transaction.builder()
//                                .id(new Random().nextInt(Integer.MAX_VALUE))
//                                .playerName("Ivan_Zalupkin")
//                                .type(CREDIT)
//                                .amount(BigDecimal.valueOf(new Random().nextInt(10000)))
//                                .identifierType(SUCCESS)
//                                .build()))
//        );
//    }
//
//    private Player getPlayer() {
//        return Player.builder()
//                .id(1)
//                .name("Ivan")
//                .password("123")
//                .balance(BigDecimal.valueOf(100))
//                .build();
//    }
//}