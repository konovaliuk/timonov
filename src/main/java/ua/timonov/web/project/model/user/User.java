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

    public User() {
    }

    public User(long id, UserType userType, String login, String password, String name) {
        this.id = id;
        this.userType = userType;
        this.login = login;
        this.password = password;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (userType != user.userType) return false;
        if (!login.equals(user.login)) return false;
        if (!password.equals(user.password)) return false;
        return name.equals(user.name);

    }

    @Override
    public int hashCode() {
        int result = userType.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + name.hashCode();
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
                '}';
    }
}
