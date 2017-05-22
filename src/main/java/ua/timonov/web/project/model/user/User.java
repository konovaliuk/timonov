package ua.timonov.web.project.model.user;

import ua.timonov.web.project.dao.Entity;

/**
 *
 */
public class User implements Entity {
    private long id;
    private UserType userType;
    private String login;
    private String password;
    private String name;
    private UserAccount account;

    public User() {
    }

    public User(UserType userType) {
        this.userType = userType;
    }

    public User(long id, UserType userType, String login, String password, String name, UserAccount account) {
        this.id = id;
        this.userType = userType;
        this.login = login;
        this.password = password;
        this.name = name;
        this.account = account;
    }

    public User(long id, String login, String password, String name, UserType userType, UserAccount account) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.userType = userType;
        this.account = account;
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

    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }
}
