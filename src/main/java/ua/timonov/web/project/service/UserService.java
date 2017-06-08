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
import ua.timonov.web.project.util.ExceptionMessages;

import java.math.BigDecimal;

/**
 * Represents service for interact with DAO layer interface UserDao and perform some logic with other DAO
 */
public class UserService extends DataService<User, Bet> {

    private static final Logger LOGGER = Logger.getLogger(UserService.class);
    private static UserDao userDao = daoFactory.createUserDao();
    private static UserAccountDao accountDao = daoFactory.createUserAccountDao();
    private static BetDao betDao = daoFactory.createBetDao();
    private static final UserService instance = new UserService();
    private static final String LOGIN_PATTERN = "[A-Za-z]{3,30}";
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";

    private UserService() {
        super(userDao, betDao);
    }

    public static UserService getInstance() {
        return instance;
    }

    /**
     * reduces user balance by given sum, checks if it's not enough money to perform operation
     * @param user
     * @param betSum
     * @throws ServiceException
     */
    public void reduceUserBalance(User user, Money betSum) throws ServiceException {
        Account account = user.getAccount();
        Money balanceBeforePay = account.getBalance();
        Money balanceAfterPay = balanceBeforePay.subtract(betSum);
        if (balanceAfterPay.getValue().compareTo(BigDecimal.ZERO) < 0) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.NOT_ENOUGH_MONEY);
            LOGGER.warn(message);
            throw new ServiceException(message);
        }
        account.setBalance(balanceAfterPay);
        accountDao.save(account);
    }

    /**
     * increases user account by won sum from won bet
     * @param bet
     * @return
     */
    public Money getWin(Bet bet) {
        Account account = bet.getUser().getAccount();
        bet.getOdds().getOddsValue();
        Money betSum = bet.getSum();
        Money wonSum = betSum.multiply(bet.getOdds().getOddsValue());
        Money balanceBeforePay = account.getBalance();
        Money balanceAfterPay = balanceBeforePay.add(wonSum);
        account.setBalance(balanceAfterPay);
        accountDao.save(account);
        return wonSum;
    }

    /**
     * returns money to account
     * @param accountId
     * @param betSum
     */
    public void returnMoney(Long accountId, Money betSum) {
        Account account = accountDao.findById(accountId);
        Money balanceBeforeReturn = account.getBalance();
        Money balanceAfterReturn = balanceBeforeReturn.add(betSum);
        account.setBalance(balanceAfterReturn);
        accountDao.save(account);
    }

    /* validates inputted login, password and password confirmation */
    public void validateUserInput(User user, String passwordConfirm) throws ServiceException {
        checkLoginSymbols(user.getLogin());
        checkPasswordSymbols(user.getPassword());
        checkIdenticalPasswords(user.getPassword(), passwordConfirm);
        findUserWithSameLogin(user);
    }

    /*  validates login symbols */
    private void checkLoginSymbols(String login) throws ServiceException {
        if (!loginMatches(login)) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.LOGIN_NOT_MATCHES);
            LOGGER.warn(message);
            throw new ServiceException(message);
        }
    }

    public boolean loginMatches(String login) {
        return login.matches(LOGIN_PATTERN);
    }

    /*  validates password symbols */
    private void checkPasswordSymbols(String password) throws ServiceException {
        if (!passwordMatches(password)) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.PASSWORD_NOT_MATCHES);
            LOGGER.warn(message);
            throw new ServiceException(message);
        }
    }

    public boolean passwordMatches(String login) {
        return login.matches(PASSWORD_PATTERN);
    }

    /**
     * checks if user inputted different passwords while signing up
     * @param password
     * @param passwordConfirm
     * @throws ServiceException
     */
    private void checkIdenticalPasswords(String password, String passwordConfirm) throws ServiceException {
        if (!passwordConfirm.equals(password)) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.DIFFERENT_PASSWORDS);
            LOGGER.warn(message);
            throw new ServiceException(message);
        }
    }

    /**
     * finds user with same login
     * @param user
     * @throws ServiceException
     */
    public void findUserWithSameLogin(User user) throws ServiceException {
        User existingUser = userDao.findByLogin(user.getLogin());
        if (existingUser != null) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.SAME_LOGIN);
            LOGGER.warn(message);
            throw new ServiceException(message);
        }
    }

    /**
     * finds user with same login and another ID
     * @param user
     * @throws ServiceException
     */
    public void findUserWithSameLoginAndAnotherId(User user) throws ServiceException {
        User existingUser = userDao.findByLogin(user.getLogin());
        if (existingUser != null && existingUser.getId() != user.getId()) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.SAME_LOGIN);
            LOGGER.warn(message);
            throw new ServiceException(message);
        }
    }

    /**
     * checks if user has proper credentials
     * @param userFromRequest
     * @return
     * @throws ServiceException
     */
    public User authorize(User userFromRequest) throws ServiceException {
        User user = userDao.findByLogin(userFromRequest.getLogin());
        if (user == null) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.WRONG_LOGIN);
            LOGGER.warn(message);
            throw new ServiceException(message);
        }
        if (!user.getPassword().equals(userFromRequest.getPassword())) {
            String message = ExceptionMessages.getMessage(ExceptionMessages.WRONG_PASSWORD);
            LOGGER.warn(message);
            throw new ServiceException(message);
        }
        return user;
    }
}
