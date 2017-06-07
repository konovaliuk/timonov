package ua.timonov.web.project.model.user;

import ua.timonov.web.project.dao.Entity;

public class Account implements Entity, Cloneable {
    private long id;
    private Money balance;

    public Account() {
    }

    public Account(Money balance) {
        this.balance = balance;
    }

    public Account(long id, Money balance) {
        this(balance);
        this.id = id;
    }

    public Account(Account account) {
        this.id = account.id;
        this.balance = account.balance;
    }

    // TODO to remove these 2 methods?
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

    @Override
    public Object clone() {
        return new Account(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;

        Account account = (Account) o;

        if (id != account.id) return false;
        return balance.equals(account.balance);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + balance.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                '}';
    }
}
