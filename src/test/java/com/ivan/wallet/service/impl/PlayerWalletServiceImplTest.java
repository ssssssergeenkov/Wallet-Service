package com.ivan.wallet.service.impl;

import com.ivan.wallet.dao.impl.PlayersDaoImpl;
import com.ivan.wallet.domain.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerWalletServiceImplTest {

    @Mock
    private PlayersDaoImpl playersDaoImpl;

    @InjectMocks
    private PlayerWalletServiceImpl playerWalletServiceImpl;

    @Test
    void currentPlayerBalance_Test() {
        String username = "username";
        String password = "password";

        Player player = new Player(1, username, password, BigDecimal.valueOf(123));

        when(playersDaoImpl.findByName(player.getName())).thenReturn(Optional.of(player));

        BigDecimal result = playerWalletServiceImpl.currentPlayerBalance(player.getName());

        assertThat(result).isNotZero();
        verify(playersDaoImpl).findByName(player.getName());
    }

    @Test
    void UpdateBalance_Test() {
        String username = "username";
        BigDecimal newBalance = BigDecimal.valueOf(500);

        playerWalletServiceImpl.updateBalance(username, newBalance);

        verify(playersDaoImpl).updatePlayerBalance(username, newBalance);
    }

    @Test
    void deleteAccount_Test() {
        String username = "username";
        String password = "password";

        Player player = new Player(1, username, password, BigDecimal.valueOf(123));

        when(playersDaoImpl.findByName(player.getName())).thenReturn(Optional.of(player));

        playerWalletServiceImpl.deleteAccount(player.getName());

        verify(playersDaoImpl).delete(player.getName());
    }
}