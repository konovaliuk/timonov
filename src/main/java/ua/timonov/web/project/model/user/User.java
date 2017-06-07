package ua.timonov.web.project.model.user;

import ua.timonov.web.project.dao.Entity;

/**
 *
 */
public class User implements Entity, Cloneable {
    private long id;
    private UserType userType;
    private String login;
    private String password;
    private String name;
    private Account account;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(UserType userType, String login, String password, String name) {
        this(login, password);
        this.userType = userType;
        this.name = name;
    }

    public User(UserType userType, String login, String password, String name, Account account) {
        this(userType, login, password, name);
        this.account = account;
    }

    public User(long id, UserType userType, String login, String password, String name) {
        this(userType, login, password, name);
        this.id = id;
    }

    public User(long id, UserType userType, String login, String password, String name, Account account) {
        this(userType, login, password, name, account);
        this.id = id;
    }

    public User(User user) {
        this.id = user.id;
        this.userType = user.userType;
        this.login = user.login;
        this.password = user.password;
        this.name = user.name;
        this.account = user.getAccount();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = (Account) account.clone();
    }

    @Override
    public Object clone() {
        return new User(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (userType != user.userType) return false;
        if (!login.equals(user.login)) return false;
        if (!password.equals(user.password)) return false;
        if (!name.equals(user.name)) return false;
        return account != null ? account.equals(user.account) : user.account == null;

    }

    @Override
    public int hashCode() {
        int result = userType.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (account != null ? account.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userType=" + userType +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", account=" + account +
                '}';
    }
}
