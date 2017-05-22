package ua.timonov.web.project.model.user;

import ua.timonov.web.project.dao.Entity;

public class UserAccount implements Entity {
    private long id;
    private double balance;

    public UserAccount() {
    }

    public UserAccount(long id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
