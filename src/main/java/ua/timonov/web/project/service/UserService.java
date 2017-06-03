package ua.timonov.web.project.service;

import org.apache.log4j.Logger;
import ua.timonov.web.project.dao.daointerface.BetDao;
import ua.timonov.web.project.dao.daointerface.UserAccountDao;
import ua.timonov.web.project.dao.daointerface.UserDao;
import ua.timonov.web.project.exception.ServiceException;
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

    public void deductUserBalance(User user, Money betSum) {
        Account account = user.getAccount();
        Money balanceBeforePay = account.getBalance();
        Money balanceAfterPay = balanceBeforePay.subtract(betSum);
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
        Money betSum = bet.getSum();
        Money wonSum = betSum.multiply(bet.getOdds().getOddsValue());
        Money balanceBeforePay = account.getBalance();
        Money balanceAfterPay = balanceBeforePay.add(wonSum);
        account.setBalance(balanceAfterPay);
        accountDao.save(account);
    }

    public void returnMoney(Bet bet) {
        Account account = bet.getUser().getAccount();
        Money balanceBeforeReturn = account.getBalance();
        Money balanceAfterReturn = balanceBeforeReturn.add(bet.getSum());
        account.setBalance(balanceAfterReturn);
        accountDao.save(account);
    }

    public void findUserWithSameLogin(User user) {
        User existingUser = userDao.findByLogin(user.getLogin());
        if (existingUser != null) {
            LOGGER.warn("There is user with the same login");
            throw new ServiceException("There is user with the same login");
        }
    }

    public User authorize(User userFromRequest) {
        User user = userDao.findByLogin(userFromRequest.getLogin());
        if (user == null) {
            LOGGER.warn("User inputted wrong login");
            throw new ServiceException("You've inputted wrong login");
        }
        if (!user.getPassword().equals(userFromRequest.getPassword())) {
            LOGGER.warn("User inputted wrong password");
            throw new ServiceException("You've inputted wrong password");
        }
        return user;
    }

    public void checkIdenticalPasswords(User user, String passwordConfirm) {
        if (!passwordConfirm.equals(user.getPassword())) {
            LOGGER.warn("User inputted different passwords");
            throw new ServiceException("You've inputted different passwords");
        }
    }
}
