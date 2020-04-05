package reservation_module.models;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Funeral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reservationDate;
    private String date;
    private String funeralDirectorId;
    private String userId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Grave grave;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFuneralDirectorId() {
        return funeralDirectorId;
    }

    public void setFuneralDirectorId(String funeralDirectorId) {
        this.funeralDirectorId = funeralDirectorId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Grave getGrave() {
        return grave;
    }

    public void setGrave(Grave grave) {
        this.grave = grave;
    }
}
