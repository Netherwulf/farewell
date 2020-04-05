package reservation_module.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Grave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reservationDate;
    private String graveNumber;
    private String coordinates;
    private String capacity;

    @OneToOne(mappedBy = "grave")
    private Funeral funeral;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grave")
    private Set<Deceased> deceased = new HashSet<>();

    public Grave addDeceased(Deceased deceased) {
        deceased.setGrave(this);
        this.deceased.add(deceased);
        return this;
    }

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

    public String getGraveNumber() {
        return graveNumber;
    }

    public void setGraveNumber(String graveNumber) {
        this.graveNumber = graveNumber;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public Funeral getFuneral() {
        return funeral;
    }

    public void setFuneral(Funeral funeral) {
        this.funeral = funeral;
    }

    public Set<Deceased> getDeceased() {
        return deceased;
    }

    public void setDeceased(Set<Deceased> deceased) {
        this.deceased = deceased;
    }
}
