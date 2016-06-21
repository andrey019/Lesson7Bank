import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "giver_id", nullable = false)
    private Account giver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Account receiver;

    private double sum;

    public Transaction() {}

    public Transaction(Account giver, Account receiver, double sum, Date date) {
        this.giver = giver;
        if (!giver.getTransactionsGiven().contains(this)) {
            giver.getTransactionsGiven().add(this);
        }
        this.receiver = receiver;
        if (!receiver.getTransactionsReceived().contains(this)) {
            receiver.getTransactionsReceived().add(this);
        }
        this.sum = sum;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getGiver() {
        return giver;
    }

    public void setGiver(Account giver) {
        this.giver = giver;
        if (!giver.getTransactionsGiven().contains(this)) {
            giver.getTransactionsGiven().add(this);
        }
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
        if (!receiver.getTransactionsReceived().contains(this)) {
            receiver.getTransactionsReceived().add(this);
        }
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
