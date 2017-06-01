package ua.timonov.web.project.model.user;

import ua.timonov.web.project.dao.Entity;

public class Account implements Entity {
    private long id;
    private Money balance;

    public Account() {
    }

    public Account(Money balance) {
        this.balance = balance;
    }

    public Account(long id, Money balance) {
        this.id = id;
        this.balance = balance;
    }

    public void addSum(Money addendum) {
        balance = balance.add(addendum);
    }

    public void deductSum(Money subtrahend) {
        balance = balance.subtract(subtrahend);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }
}
