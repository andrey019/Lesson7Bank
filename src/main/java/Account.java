import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private long number;

    @Column(nullable = false)
    private double money;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rate_id", nullable = false)
    private Rate rate;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "giver", cascade = CascadeType.REFRESH)
    private Set<TransactionBank> transactionsGiven = new HashSet<>();

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.REFRESH)
    private Set<TransactionBank> transactionsReceived = new HashSet<>();

    public Account() {}

    public Account(long number, double money, Client client, Rate rate) {
        this.number = number;
        this.money = money;
        setClient(client);
        setRate(rate);
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
        if (!client.getAccounts().contains(this)) {
            client.getAccounts().add(this);
        }
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void addMoney(double money) {
        this.money += money;
    }

    public void subMoney(double money) {
        this.money -= money;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
        if (!rate.getAccounts().contains(this)) {
            rate.getAccounts().add(this);
        }
    }

    public Set<TransactionBank> getTransactionsGiven() {
        return transactionsGiven;
    }

    public void setTransactionsGiven(Set<TransactionBank> transactionsGiven) {
        this.transactionsGiven = transactionsGiven;
    }

    public Set<TransactionBank> getTransactionsReceived() {
        return transactionsReceived;
    }

    public void setTransactionsReceived(Set<TransactionBank> transactionsReceived) {
        this.transactionsReceived = transactionsReceived;
    }

    public void addTransactionGiven(TransactionBank transaction) {
        transactionsGiven.add(transaction);
        if (transaction.getGiver() != this) {
            transaction.setGiver(this);
        }
    }

    public void addTransactionReceived(TransactionBank transaction) {
        transactionsReceived.add(transaction);
        if (transaction.getReceiver() != this) {
            transaction.setReceiver(this);
        }
    }

    @Override
    public String toString() {
        return "id = " + id + ";\t number = " + number + "; owner = " + client.getName() + "\t " +
                client.getSurname() + ";\t money = " + money + " " + rate.getCurrency();
    }
}
