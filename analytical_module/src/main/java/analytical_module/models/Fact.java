package analytical_module.models;


import javax.persistence.*;

@Entity
public class Fact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String funeralId;
    private String funeralReservationDate;
    private String funeralDate;
    private String graveId;
    private String graveReservationDate;
    private String graveNumber;
    private String graveCoordinates;
    private String graveCapacity;
    private String deceasedId;
    private String deceasedSurname;
    private String deceasedName;
    private String deceasedDateOfBirth;
    private String deceasedPlaceOfBirth;
    private String deceasedDateOfDeath;
    private String deceasedPlaceOfDeath;
    private String creationDate;
    private String userId;

    @ManyToOne(cascade = {CascadeType.ALL})
    private FuneralDirector funeralDirector;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFuneralId() {
        return funeralId;
    }

    public void setFuneralId(String funeralId) {
        this.funeralId = funeralId;
    }

    public String getFuneralReservationDate() {
        return funeralReservationDate;
    }

    public void setFuneralReservationDate(String funeralReservationDate) {
        this.funeralReservationDate = funeralReservationDate;
    }

    public String getFuneralDate() {
        return funeralDate;
    }

    public void setFuneralDate(String funeralDate) {
        this.funeralDate = funeralDate;
    }

    public String getGraveId() {
        return graveId;
    }

    public void setGraveId(String graveId) {
        this.graveId = graveId;
    }

    public String getGraveReservationDate() {
        return graveReservationDate;
    }

    public void setGraveReservationDate(String graveReservationDate) {
        this.graveReservationDate = graveReservationDate;
    }

    public String getGraveNumber() {
        return graveNumber;
    }

    public void setGraveNumber(String graveNumber) {
        this.graveNumber = graveNumber;
    }

    public String getGraveCoordinates() {
        return graveCoordinates;
    }

    public void setGraveCoordinates(String graveCoordinates) {
        this.graveCoordinates = graveCoordinates;
    }

    public String getGraveCapacity() {
        return graveCapacity;
    }

    public void setGraveCapacity(String graveCapacity) {
        this.graveCapacity = graveCapacity;
    }

    public String getDeceasedId() {
        return deceasedId;
    }

    public void setDeceasedId(String deceasedId) {
        this.deceasedId = deceasedId;
    }

    public String getDeceasedSurname() {
        return deceasedSurname;
    }

    public void setDeceasedSurname(String deceasedSurname) {
        this.deceasedSurname = deceasedSurname;
    }

    public String getDeceasedName() {
        return deceasedName;
    }

    public void setDeceasedName(String deceasedName) {
        this.deceasedName = deceasedName;
    }

    public String getDeceasedDateOfBirth() {
        return deceasedDateOfBirth;
    }

    public void setDeceasedDateOfBirth(String deceasedDateOfBirth) {
        this.deceasedDateOfBirth = deceasedDateOfBirth;
    }

    public String getDeceasedPlaceOfBirth() {
        return deceasedPlaceOfBirth;
    }

    public void setDeceasedPlaceOfBirth(String deceasedPlaceOfBirth) {
        this.deceasedPlaceOfBirth = deceasedPlaceOfBirth;
    }

    public String getDeceasedDateOfDeath() {
        return deceasedDateOfDeath;
    }

    public void setDeceasedDateOfDeath(String deceasedDateOfDeath) {
        this.deceasedDateOfDeath = deceasedDateOfDeath;
    }

    public String getDeceasedPlaceOfDeath() {
        return deceasedPlaceOfDeath;
    }

    public void setDeceasedPlaceOfDeath(String deceasedPlaceOfDeath) {
        this.deceasedPlaceOfDeath = deceasedPlaceOfDeath;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public FuneralDirector getFuneralDirector() {
        return funeralDirector;
    }

    public void setFuneralDirector(FuneralDirector funeralDirector) {
        this.funeralDirector = funeralDirector;
    }
}
