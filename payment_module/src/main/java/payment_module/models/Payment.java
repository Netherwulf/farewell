package payment_module.models;

import javax.persistence.*;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long amount;
    private boolean reservation;
    private boolean purchase;
    private boolean grave;
    private boolean funeral;
    private Long userId;

    //    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(cascade = {CascadeType.ALL})

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public boolean getReservation() {
        return reservation;
    }

    public void setReservation(boolean reservation) {
        this.reservation = reservation;
    }

    public boolean getPurchase() {
        return purchase;
    }

    public void setPurchase(boolean purchase) {
        this.purchase = purchase;
    }

    public boolean getGrave() {
        return grave;
    }

    public void setGrave(boolean grave) {
        this.grave = grave;
    }

    public boolean getFunreal() {
        return funeral;
    }

    public void setFunreal(boolean funeral) {
        this.funeral = funeral;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
}
