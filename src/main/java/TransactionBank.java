import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class TransactionBank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "giver_id", nullable = false)
    private Account giver;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Account receiver;

    @Column(nullable = false)
    private double initialSum;

    @Column(nullable = false)
    private double finalSum;

    public TransactionBank() {}

    public TransactionBank(Account giver, Account receiver, double initialSum, Date date) {
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
            if ( (initialSum > 0) && (giver.getMoney() >= initialSum) ) {
                if ( receiver.getRate().getId() == giver.getRate().getId() ) {
                    finalSum = initialSum;
                    return true;
                } else {
                    double giverConvertion = initialSum * giver.getRate().getBuyRate();
                    double receiverConvertion = giverConvertion / receiver.getRate().getSellRate();
                    finalSum = receiverConvertion;
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean setFinalSum(Rate giver) {
        try {
            if ( receiver.getRate().getId() == giver.getId() ) {
                finalSum = initialSum;
                return true;
            } else {
                double giverConvertion = initialSum * giver.getBuyRate();
                double receiverConvertion = giverConvertion / receiver.getRate().getSellRate();
                finalSum = receiverConvertion;
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        return "id = " + id + ";\t date = " + date +"; giver = " + giver.getNumber() + "; receiver = " +
                receiver.getNumber() + "; initial sum = " + initialSum + ";\t final sum = " + finalSum;
    }
}
