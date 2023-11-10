package com.ivan.wallet.service;

import com.ivan.wallet.dao.AuditsDao;
import com.ivan.wallet.domain.Audits;
import com.ivan.wallet.domain.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static com.ivan.wallet.domain.types.ActionType.*;
import static com.ivan.wallet.domain.types.IdentifierType.FAIL;
import static com.ivan.wallet.domain.types.IdentifierType.SUCCESS;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditWalletServiceTest {
    @Mock
    private AuditsDao auditsDao;
    @InjectMocks
    private AuditWalletService auditWalletService;

    @BeforeEach()
    void setUp() {
    }

    @ParameterizedTest
    @MethodSource("getArguments")
    void showAudit(String username, List<Audits> auditsList) {
        when(auditsDao.findAllByName(username)).thenReturn(auditsList);

        auditWalletService.showAudit(username);

        verify(auditsDao).findAllByName(username);
    }

    static Stream<Arguments> getArguments() {
        return Stream.of(
                Arguments.of("Ivan_Pupkin", Arrays.asList(
                        Audits.builder()
                                .id(new Random().nextInt(Integer.MAX_VALUE))
                                .playerName("Ivan_Pupkin")
                                .actionType(REGISTRATION_ACTION)
                                .identifierType(SUCCESS)
                                .build())),

                Arguments.of("Ivan_Popkin", Arrays.asList(
                        Audits.builder()
                                .id(new Random().nextInt(Integer.MAX_VALUE))
                                .playerName("Ivan_Popkin")
                                .actionType(AUTHORIZATION_ACTION)
                                .identifierType(SUCCESS)
                                .build(),

                        Audits.builder()
                                .id(new Random().nextInt(Integer.MAX_VALUE))
                                .playerName("Ivan_Popkin")
                                .actionType(CURRENT_BALANCE_ACTION)
                                .identifierType(SUCCESS)
                                .build())),


                Arguments.of("Ivan_Zalupkin", Arrays.asList(
                        Audits.builder()
                                .id(new Random().nextInt(Integer.MAX_VALUE))
                                .playerName("Ivan_Popkin")
                                .actionType(AUTHORIZATION_ACTION)
                                .identifierType(SUCCESS)
                                .build(),

                        Audits.builder()
                                .id(new Random().nextInt(Integer.MAX_VALUE))
                                .playerName("Ivan_Zalupkin")
                                .actionType(DEBIT_ACTION)
                                .identifierType(FAIL)
                                .build(),

                        Audits.builder()
                                .id(new Random().nextInt(Integer.MAX_VALUE))
                                .playerName("Ivan_Zalupkin")
                                .actionType(CREDIT_ACTION)
                                .identifierType(SUCCESS)
                                .build()))
        );
    }
}