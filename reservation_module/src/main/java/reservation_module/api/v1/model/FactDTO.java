package reservation_module.api.v1.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FactDTO {
    private Long id;

    private String funeralId;
    private String funeralReservationDate;
    private String funeralPurchaseDate;
    private String funeralDate;
    private String graveId;
    private String graveReservationDate;
    private String gravePurchaseDate;
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

    private Long funeralDirectorId;
}
