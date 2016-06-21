import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_id", nullable = false)
    private Rate rate;

    @OneToMany(mappedBy = "giver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactionsGiven = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactionsReceived = new ArrayList<>();

    public Account() {}


    //private List<Transaction> transactions = new ArrayList<>();


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

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
        if (!rate.getAccounts().contains(this)) {
            rate.getAccounts().add(this);
        }
    }

    public List<Transaction> getTransactionsGiven() {
        return transactionsGiven;
    }

    public void setTransactionsGiven(List<Transaction> transactionsGiven) {
        this.transactionsGiven = transactionsGiven;
    }

    public List<Transaction> getTransactionsReceived() {
        return transactionsReceived;
    }

    public void setTransactionsReceived(List<Transaction> transactionsReceived) {
        this.transactionsReceived = transactionsReceived;
    }

    public void addTransactionGiven(Transaction transaction) {
        transactionsGiven.add(transaction);
        if (transaction.getGiver() != this) {
            transaction.setGiver(this);
        }
    }

    public void addTransactionReceived(Transaction transaction) {
        transactionsReceived.add(transaction);
        if (transaction.getReceiver() != this) {
            transaction.setReceiver(this);
        }
    }
}
