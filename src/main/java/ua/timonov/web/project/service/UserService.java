package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.BetDao;
import ua.timonov.web.project.dao.daointerface.UserAccountDao;
import ua.timonov.web.project.dao.daointerface.UserDao;
import ua.timonov.web.project.model.bet.Bet;
import ua.timonov.web.project.model.user.Account;
import ua.timonov.web.project.model.user.Money;
import ua.timonov.web.project.model.user.User;

import java.math.BigDecimal;

public class UserService extends DataService<User, Bet> {

    private static final Logger LOGGER = Logger.getLogger(UserService.class);
    private static UserDao userDao = daoFactory.createUserDao();
    private static UserAccountDao accountDao = daoFactory.createUserAccountDao();
    private static BetDao betDao = daoFactory.createBetDao();
    private static final UserService instance = new UserService();

    private UserService() {
        super(userDao, betDao);
    }

    public static UserService getInstance() {
        return instance;
    }

    public void deductUserBalance(User user, BigDecimal betSum) {
        Account account = user.getAccount();
        Money balanceBeforePay = account.getBalance();
        Money balanceAfterPay = balanceBeforePay.subtract(new Money(betSum));
        if (balanceAfterPay.getValue().compareTo(BigDecimal.ZERO) < 0) {
            LOGGER.error("There is not enough money on your account");
            throw new SecurityException("There is not enough money on your account");
        }
        account.setBalance(balanceAfterPay);
        accountDao.save(account);
    }

    public void payWin(Bet bet) {
        Account account = bet.getUser().getAccount();
        bet.getOdds().getOddsValue();
        Money betSum = new Money(bet.getSum());
        Money wonSum = betSum.multiply(bet.getOdds().getOddsValue());
        Money balanceBeforePay = account.getBalance();
        Money balanceAfterPay = balanceBeforePay.add(wonSum);
        account.setBalance(balanceAfterPay);
        accountDao.save(account);
    }

    public void returnMoney(Bet bet) {
        Account account = bet.getUser().getAccount();
        Money betSum = new Money(bet.getSum());
        Money balanceBeforeReturn = account.getBalance();
        Money balanceAfterReturn = balanceBeforeReturn.add(betSum);
        account.setBalance(balanceAfterReturn);
        betDao.delete(bet.getId());
        accountDao.save(account);
    }
}
