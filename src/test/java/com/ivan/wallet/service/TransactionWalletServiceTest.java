package com.ivan.wallet.service;

import com.ivan.wallet.dao.AuditsDao;
import com.ivan.wallet.dao.PlayersDao;
import com.ivan.wallet.dao.TransactionsDao;
import com.ivan.wallet.domain.Audits;
import com.ivan.wallet.domain.Player;
import com.ivan.wallet.domain.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

import static com.ivan.wallet.domain.types.ActionType.DEBIT_ACTION;
import static com.ivan.wallet.domain.types.ActionType.REGISTRATION_ACTION;
import static com.ivan.wallet.domain.types.IdentifierType.FAIL;
import static com.ivan.wallet.domain.types.IdentifierType.SUCCESS;
import static com.ivan.wallet.domain.types.TransactionType.CREDIT;
import static com.ivan.wallet.domain.types.TransactionType.DEBIT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TransactionWalletServiceTest {

    @Mock
    private AuditsDao auditsDao;
    @Mock
    private PlayersDao playersDao;
    @Mock
    private TransactionsDao transactionsDao;

    @InjectMocks
    private AuditWalletService auditWalletService;
    @InjectMocks
    private PlayerWalletService playerWalletService;
    @InjectMocks
    private TransactionWalletService transactionWalletService;

    @Test
    void debit_Successful() {
        Player player = getPlayer();

        BigDecimal debitAmount =  BigDecimal.valueOf(30);

        when(playersDao.findByName(player.getName())).thenReturn(Optional.of(player));

        transactionWalletService.debit(player.getName(), debitAmount);

        //сверяет что баланс уменьшился. 70 и (player.getBalance() (100-30=70))
        assertEquals(BigDecimal.valueOf(70), player.getBalance());
        verify(playersDao, times(1)).update(player);
        verify(transactionsDao, times(1)).createTransaction(player.getName(), DEBIT, debitAmount, SUCCESS);
        verify(auditsDao, times(1)).createAudit(player.getName(), DEBIT_ACTION, SUCCESS);
    }

    @Test
    void debit_Failed_Because_The_Player_Does_Not_Exist() {
        Player player = getPlayer();

        when(playersDao.findByName(player.getName())).thenReturn(Optional.empty());

        transactionWalletService.debit(player.getName(), BigDecimal.valueOf(50));

        verify(playersDao, times(0)).update(player);
        verify(transactionsDao, times(0)).createTransaction(player.getName(), DEBIT, BigDecimal.valueOf(50), SUCCESS);
        verify(auditsDao, times(0)).createAudit(player.getName(), DEBIT_ACTION, FAIL);
    }

    @Test
    void debit_Failed_Because_The_Player_Does_Not_Have_Enough_Funds() {
        Player player = getPlayer();

        BigDecimal debitAmount = BigDecimal.valueOf(110);

        when(playersDao.findByName(player.getName())).thenReturn(Optional.of(player));

        transactionWalletService.debit(player.getName(), debitAmount);

        verify(playersDao, times(0)).update(player);
        verify(transactionsDao, times(0)).createTransaction(player.getName(), DEBIT, BigDecimal.valueOf(50), SUCCESS);
        verify(auditsDao, times(1)).createAudit(player.getName(), DEBIT_ACTION, FAIL);
    }


    @Test
    void credit() {
        //завтра напишу
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    void showTransactionHistory(String username, List<Transaction> transactionList) {
        when(transactionsDao.findAllByName(username)).thenReturn(transactionList);

        transactionWalletService.showTransactionHistory(username);

        verify(transactionsDao).findAllByName(username);
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

    private Player getPlayer() {
        return Player.builder()
                .id(1)
                .name("Ivan")
                .password("123")
                .balance(BigDecimal.valueOf(100))
                .build();
    }

    private Audits getAudit() {
        return Audits.builder()
                .id(1)
                .playerName("Ivan")
                .actionType(REGISTRATION_ACTION)
                .identifierType(SUCCESS)
                .build();
    }

    private Transaction getDebetTransaction() {
        return Transaction.builder()
                .id(1)
                .playerName("Ivan")
                .type(DEBIT)
                .amount(BigDecimal.valueOf(100))
                .identifierType(SUCCESS)
                .build();
    }
}