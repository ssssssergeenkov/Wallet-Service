package com.ivan.wallet.service;

import com.ivan.wallet.dao.AuditsDao;
import com.ivan.wallet.dao.PlayersDao;
import com.ivan.wallet.domain.Audits;
import com.ivan.wallet.domain.Player;
import com.ivan.wallet.in.WalletConsole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static com.ivan.wallet.domain.types.ActionType.*;
import static com.ivan.wallet.domain.types.IdentifierType.SUCCESS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerWalletServiceTest {

    @Mock
    private PlayersDao playersDao;
    @Mock
    private AuditsDao auditsDao;
    @Mock
    private WalletConsole walletConsole;

    @InjectMocks
    private PlayerWalletService playerWalletService;

    @Test
    void registration_Successful() {
        String username = "username"; //given
        String password = "password";
        Player player = getPlayer();
        Audits audits = getAudit();

        when(playersDao.findByName(username)).thenReturn(Optional.empty());
        when(playersDao.createUser(username, password)).thenReturn(player);
        when(auditsDao.createAudit(player.getName(), REGISTRATION_ACTION, SUCCESS)).thenReturn(audits);

//        doReturn(Optional.empty()).when(playersDao.findByName(username)); //такой подход не работает
//        doReturn(player).when(playersDao.createUser(username, password));

        boolean result = playerWalletService.registration(username, password); //when

        assertThat(result).isTrue(); //then
        verify(playersDao).findByName(username);
        verify(playersDao).createUser(username, password);
        verify(auditsDao).createAudit(player.getName(), REGISTRATION_ACTION, SUCCESS);
    }

    //короче тут нужно использовать параметризованные
    @Test
    void registration_Is_Failed_Because_The_Player_Already_Exists() {
        String username = "username";
        String password = "password";
        Player player = getPlayer();

        when(playersDao.findByName(username)).thenReturn(Optional.of(player));

        boolean result = playerWalletService.registration(username, password);

        assertThat(result).isFalse();
        verify(playersDao).findByName(username);
        verify(playersDao, never()).createUser(username, password);
        verify(auditsDao, never()).createAudit(player.getName(), REGISTRATION_ACTION, SUCCESS);
    }

    @Test
    void authorization_Successful() {
        String username = "username";
        Player player = getPlayer();

        when(playersDao.findByName(username)).thenReturn(Optional.of(player));

        boolean result = playerWalletService.authorization(username, player.getPassword());

        assertThat(result).isTrue();
        verify(playersDao).findByName(username);
        verify(auditsDao).createAudit(username, AUTHORIZATION_ACTION, SUCCESS);
    }

    @Test
    void authorization_Is_Failed_Because_Username_Is_Incorrect() {
        Player player = getPlayer();

        when(playersDao.findByName(any())).thenReturn(Optional.empty());

        boolean result = playerWalletService.authorization(any(), player.getPassword());

        assertThat(result).isFalse();
        verify(playersDao).findByName(any());
    }

    @Test
    void authorization_Is_Failed_Because_Password_Is_Incorrect() {
        Player player = getPlayer();

        when(playersDao.findByName(player.getName())).thenReturn(Optional.of(player));

        boolean result = playerWalletService.authorization(player.getName(), any());

        assertThat(result).isFalse();
        verify(playersDao).findByName(player.getName());
    }


    @Test
    void currentPlayerBalance_Available() {
        Player player = getPlayer();

        when(playersDao.findByName(player.getName())).thenReturn(Optional.of(player));

        BigDecimal result = playerWalletService.currentPlayerBalance(player.getName());

        assertThat(result).isNotZero();
        verify(playersDao).findByName(player.getName());
    }

    @Test
    void currentPlayerBalance_Is_Not_Available_Because_The_User_Does_Not_Exist() {
        Player player = getPlayer();

        when(playersDao.findByName(player.getName())).thenReturn(Optional.empty());

        BigDecimal result = playerWalletService.currentPlayerBalance(player.getName());

        assertThat(result).isZero();
        verify(playersDao).findByName(player.getName());
    }

    @Test
    void logOut_Test() {
        playerWalletService.logOut(walletConsole);

        verify(walletConsole).setLoggedInUserName(null);
        verify(walletConsole).setLogIn(false);
    }

    @Test
    void deleteAccount_Test() {
        Player player = getPlayer();
        Audits audits = getAudit();

        when(playersDao.findByName(player.getName())).thenReturn(Optional.of(player));
        when(auditsDao.createAudit(player.getName(), DELETE_ACTION, SUCCESS)).thenReturn(audits);

        playerWalletService.deleteAccount(player.getName());

        verify(auditsDao).createAudit(player.getName(), DELETE_ACTION, SUCCESS);
        verify(playersDao).delete(player.getName());
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
}