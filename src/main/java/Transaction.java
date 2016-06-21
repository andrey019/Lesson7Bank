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

    @Column(nullable = false)
    private double initialSum;

    @Column(nullable = false)
    private double finalSum;

    public Transaction() {}

    public Transaction(Account giver, Account receiver, double initialSum, Date date) {
        this.giver = giver;
        if (!giver.getTransactionsGiven().contains(this)) {
            giver.getTransactionsGiven().add(this);
        }
        this.receiver = receiver;
        if (!receiver.getTransactionsReceived().contains(this)) {
            receiver.getTransactionsReceived().add(this);
        }
        this.initialSum = initialSum;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getInitialSum() {
        return initialSum;
    }

    public void setInitialSum(double initialSum) {
        this.initialSum = initialSum;
    }

    public double getFinalSum() {
        return finalSum;
    }

    public boolean setFinalSum() {
        try {
            if ( (initialSum > 0) && (receiver != null) && (giver != null)) {
                if (receiver.getRate().equals(giver.getRate())) {
                    giver.setMoney((giver.getMoney() - initialSum));
                    receiver.setMoney((receiver.getMoney() + initialSum));
                    finalSum = initialSum;
                    return true;
                } else {
                    giver.setMoney((giver.getMoney() - initialSum));
                    double giverConvertion = initialSum * giver.getRate().getSellRate();
                    double receiverConvertion = giverConvertion / receiver.getRate().getBuyRate();
                    receiver.setMoney((receiver.getMoney() + receiverConvertion));
                    finalSum = receiverConvertion;
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
