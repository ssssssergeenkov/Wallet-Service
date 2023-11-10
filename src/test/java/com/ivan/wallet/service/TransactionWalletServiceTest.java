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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

import static com.ivan.wallet.domain.types.ActionType.REGISTRATION_ACTION;
import static com.ivan.wallet.domain.types.IdentifierType.FAIL;
import static com.ivan.wallet.domain.types.IdentifierType.SUCCESS;
import static com.ivan.wallet.domain.types.TransactionType.CREDIT;
import static com.ivan.wallet.domain.types.TransactionType.DEBIT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void debit() {
        Player player = getPlayer();
        Audits audits = getAudit();
        Transaction transaction = getDebetTransaction();

        when(playersDao.findByName(player.getName())).thenReturn(Optional.of(player));

        assertThat(player.getBalance()).isNotNull();
//        assertTrue(player.getBalance() < transaction.getId());


    }

    @Test
    void credit() {

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
                .balance(BigDecimal.valueOf(123))
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