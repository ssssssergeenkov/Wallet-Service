package com.ivan.wallet.service;

import com.ivan.wallet.domain.Player;

/**
 * The interface Security service.
 */
public interface SecurityService {

    Player registration(String username, String password);

    Player authorization(String username, String password);
}
