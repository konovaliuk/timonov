package ua.timonov.web.project.model.user;

public class Client extends User {
    private Account account;

    public Client() {
    }

    public Client(long id, UserType userType, String login, String password, String name, Account account) {
        super(id, userType, login, password, name);
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        if (!super.equals(o)) return false;

        Client client = (Client) o;

        return account.equals(client.account);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + account.hashCode();
        return result;
    }
}
