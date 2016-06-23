import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String currency;

    @Column(nullable = false)
    private double buyRate;

    @Column(nullable = false)
    private double sellRate;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "rate", cascade = CascadeType.ALL)
    private Set<Account> accounts = new HashSet<>();

    public Rate() {}

    public Rate(String currency, double buyRate, double sellRate) {
        this.currency = currency;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
    }

    public void addAccount(Account account) {
        accounts.add(account);
        if (account.getRate() != this) {
            account.setRate(this);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getBuyRate() {
        return buyRate;
    }

    public void setBuyRate(double buyRate) {
        this.buyRate = buyRate;
    }

    public double getSellRate() {
        return sellRate;
    }

    public void setSellRate(double sellRate) {
        this.sellRate = sellRate;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "id = " + id + ";\t currency = " + currency + "; buy rate = " + buyRate + ";\t sell rate = " + sellRate;
    }
}
