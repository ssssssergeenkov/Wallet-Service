package com.ivan.wallet.service.impl;

import com.ivan.wallet.dao.impl.AuditsDaoImpl;
import com.ivan.wallet.dao.impl.PlayersDaoImpl;
import com.ivan.wallet.domain.Audits;
import com.ivan.wallet.domain.Player;
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

import static com.ivan.wallet.domain.types.ActionType.*;
import static com.ivan.wallet.domain.types.IdentifierType.FAIL;
import static com.ivan.wallet.domain.types.IdentifierType.SUCCESS;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminWalletServiceImplTest {

    @Mock
    private AuditsDaoImpl auditsDaoImpl;
    @Mock
    private PlayersDaoImpl playersDaoImpl;

    @InjectMocks
    private AdminWalletServiceImpl adminWalletServiceImpl;

    @ParameterizedTest
    @MethodSource("get_ShowAudit_Arguments")
    void showAudit(String username, List<Audits> auditsList) {
        when(auditsDaoImpl.findByName(username)).thenReturn(auditsList);

        adminWalletServiceImpl.showAudit(username);

        verify(auditsDaoImpl).findByName(username);
    }

    static Stream<Arguments> get_ShowAudit_Arguments() {
        return Stream.of(
                Arguments.of("Ivan_Pupkin", Collections.singletonList(
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

    @ParameterizedTest
    @MethodSource("get_ShowAllPlayers_Argument")
    public void ShowAllPlayers_Test(List<Player> playerList) {
        when(playersDaoImpl.findAll()).thenReturn(playerList);

        adminWalletServiceImpl.showAllPlayers();

        verify(playersDaoImpl).findAll();
    }

    static Stream<Arguments> get_ShowAllPlayers_Argument() {
        return Stream.of(
                Arguments.of(Collections.singletonList(Player.builder()
                        .id(1)
                        .name("Ivan")
                        .password("123")
                        .balance(BigDecimal.ZERO)
                        .build())),

                Arguments.of(Collections.singletonList(Player.builder()
                        .id(2)
                        .name("Petr")
                        .password("321")
                        .balance(BigDecimal.ZERO)
                        .build())),

                Arguments.of(Collections.singletonList(Player.builder()
                        .id(3)
                        .name("Nikita")
                        .password("155")
                        .balance(BigDecimal.ZERO)
                        .build()))
        );
    }
}