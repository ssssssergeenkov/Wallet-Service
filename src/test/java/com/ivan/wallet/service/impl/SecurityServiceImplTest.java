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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityServiceImplTest {

    @Mock
    private PlayersDaoImpl playersDaoImpl;

    @InjectMocks
    private SecurityServiceImpl securityServiceImpl;

    @Test
    void registration_Successful() {
        String username = "username"; //given
        String password = "password";
        Player player = new Player(1, username, password, BigDecimal.valueOf(123));

        when(playersDaoImpl.findByName(username)).thenReturn(Optional.empty());
        when(playersDaoImpl.save(any(Player.class))).thenReturn(player);

        Player result = securityServiceImpl.registration(username, password); //when

        assertThat(result).isNotNull(); //then
        assertThat(username).isEqualTo(result.getName());
        verify(playersDaoImpl).findByName(username);
    }

    @Test
    void registration_Is_Failed_Because_The_Player_Already_Exists() {
        String username = "username";
        String password = "password";
        Player player = new Player(1, username, password, BigDecimal.valueOf(123));

        when(playersDaoImpl.findByName(username)).thenReturn(Optional.of(player));

        Player result = securityServiceImpl.registration(username, password);

        assertThat(result).isNull();
        verify(playersDaoImpl).findByName(username);
    }

    @Test
    void authorization_Successful() {
        String username = "username";
        String password = "password";

        Player player = new Player(1, username, password, BigDecimal.valueOf(123));

        when(playersDaoImpl.findByName(username)).thenReturn(Optional.of(player));

        Player result = securityServiceImpl.authorization(username, player.getPassword());

        assertThat(result).isNotNull();
        assertThat(username).isEqualTo(result.getName());
        verify(playersDaoImpl).findByName(username);
    }

    @Test
    void authorization_Is_Failed_Because_Username_Is_Incorrect() {
        String username = "username";
        String password = "password";

        Player player = new Player(1, username, password, BigDecimal.valueOf(123));

        when(playersDaoImpl.findByName(any())).thenReturn(Optional.empty());

        Player result = securityServiceImpl.authorization(any(), player.getPassword());

        assertThat(result).isNull();
        verify(playersDaoImpl).findByName(any());
    }

    @Test
    void authorization_Is_Failed_Because_Password_Is_Incorrect() {
        String username = "username";
        String password = "password";

        Player player = new Player(1, username, password, BigDecimal.valueOf(123));

        when(playersDaoImpl.findByName(player.getName())).thenReturn(Optional.of(player));

        Player result = securityServiceImpl.authorization(player.getName(), any());

        assertThat(result).isNull();
        verify(playersDaoImpl).findByName(player.getName());
    }
}