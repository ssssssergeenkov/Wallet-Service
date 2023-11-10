package com.ivan.wallet.in;
import com.ivan.wallet.in.handlers.AdminHandler;
import com.ivan.wallet.in.handlers.AppRunnerHandler;
import com.ivan.wallet.in.handlers.PlayerHandler;
import com.ivan.wallet.service.wrapperService.WrapperAuditService;
import com.ivan.wallet.service.wrapperService.WrapperPlayerService;
import com.ivan.wallet.service.wrapperService.WrapperTransactionService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WalletConsole {
    private static final WalletConsole INSTANCE = new WalletConsole();

    public static WalletConsole getINSTANCE() {
        return INSTANCE;
    }

    WrapperPlayerService wrapperPlayerService = WrapperPlayerService.getINSTANCE();
    WrapperTransactionService wrapperTransactionService = WrapperTransactionService.getINSTANCE();
    WrapperAuditService wrapperAuditService = WrapperAuditService.getINSTANCE();

    @Getter
    @Setter
    String loggedInUserName = null;

    @Getter
    @Setter
    boolean logIn = false;

    /**
     * Запускает приложение и обрабатывает взаимодействие с пользователем.
     * Выводит меню действий и обрабатывает выбор пользователя.
     * В зависимости от выбора пользователя выполняет соответствующие действия,
     * такие как регистрация игрока, авторизация, дебетовая или кредитная транзакция,
     * просмотр истории транзакций и аудит действий игрока.
     */
    public void start() {
        AdminHandler adminHandler = new AdminHandler();
        AppRunnerHandler appRunnerHandler = new AppRunnerHandler();
        PlayerHandler playerHandler = new PlayerHandler();

        while (true) {
            if (!logIn || loggedInUserName == null) {
                appRunnerHandler.displayAppRunnerMenu();
                int choice = appRunnerHandler.readChoice();

                switch (choice) {
                    case 1 -> wrapperPlayerService.wrapperRegistration();
                    case 2 -> wrapperPlayerService.wrapperAuthorization(INSTANCE);
                    case 3 -> wrapperPlayerService.wrapperExit();
                    default -> wrapperPlayerService.wrapperIncorrect();
                }
            }
            if ("admin".equals(loggedInUserName)) {
                adminHandler.displayAdminMenu();
                int choice = appRunnerHandler.readChoice();

                switch (choice) {
                    case 1 -> wrapperAuditService.wrapperAudit();
                    case 2 -> wrapperPlayerService.wrapperAdminDeleteAccount(INSTANCE);
                    case 3 -> wrapperPlayerService.wrapperLogOut(INSTANCE);
                    case 4 -> wrapperPlayerService.wrapperExit();
                    default -> wrapperPlayerService.wrapperIncorrect();
                }
            } else if (loggedInUserName != null) {
                playerHandler.displayPlayerMenu();
                int choice = appRunnerHandler.readChoice();

                switch (choice) {
                    case 1 -> wrapperPlayerService.wrapperCurrentPlayerBalance(INSTANCE);
                    case 2 -> wrapperTransactionService.wrapperDebit(INSTANCE);
                    case 3 -> wrapperTransactionService.wrapperCredit(INSTANCE);
                    case 4 -> wrapperTransactionService.wrapperTransactionHistory(INSTANCE);
                    case 5 -> wrapperPlayerService.wrapperLogOut(INSTANCE);
                    case 6 -> wrapperPlayerService.wrapperExit();
                    case 7 -> wrapperPlayerService.wrapperDeleteAccount(INSTANCE);
                    default -> wrapperPlayerService.wrapperIncorrect();
                }
            }
        }
    }
}