package analytical_module.controllers;

import analytical_module.api.v1.model.DeceasedDTO;
import analytical_module.api.v1.model.GraveDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import analytical_module.api.v1.model.FuneralDTO;
import analytical_module.services.FactService;
import analytical_module.api.v1.model.FactDTO;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

@Component
public class RabbitMQReceiver {

    private final FactService factService;

    public RabbitMQReceiver(FactService factService) {
        this.factService = factService;
    }

    @RabbitListener(queues = "${sample.rabbitmq.queue}")
    public void recievedMessage(FuneralDTO funeralDTO) {
        System.out.println("Received Message From RabbitMQ: " + funeralDTO);

        if (funeralDTO.getGrave() != null && !Objects.equals(funeralDTO.getGrave().getDeceased(), new HashSet<>())) {
            for (DeceasedDTO deceasedDTO : funeralDTO.getGrave().getDeceased()) {

                FactDTO factDTO = new FactDTO();

                // funeral data
                factDTO.setFuneralId(String.valueOf(funeralDTO.getId()));
                factDTO.setFuneralReservationDate(funeralDTO.getReservationDate());
                factDTO.setFuneralPurchaseDate(null);
                factDTO.setFuneralDate(funeralDTO.getDate());
                factDTO.setFuneralDirectorId(Long.valueOf(funeralDTO.getFuneralDirectorId()));

                // grave data
                GraveDTO graveDTO = funeralDTO.getGrave();
                factDTO.setGraveId(String.valueOf(graveDTO.getId()));
                factDTO.setGraveReservationDate(graveDTO.getReservationDate());
                factDTO.setGravePurchaseDate(null);
                factDTO.setGraveNumber(graveDTO.getGraveNumber());
                factDTO.setGraveCoordinates(graveDTO.getCoordinates());
                factDTO.setGraveCapacity(graveDTO.getCapacity());

                // deceased data
                factDTO.setDeceasedId(String.valueOf(deceasedDTO.getId()));
                factDTO.setDeceasedSurname(deceasedDTO.getSurname());
                factDTO.setDeceasedName(deceasedDTO.getName());
                factDTO.setDeceasedDateOfBirth(deceasedDTO.getDateOfBirth());
                factDTO.setDeceasedPlaceOfBirth(deceasedDTO.getPlaceOfBirth());
                factDTO.setDeceasedDateOfDeath(deceasedDTO.getDateOfDeath());
                factDTO.setDeceasedPlaceOfDeath(deceasedDTO.getPlaceOfDeath());

                // creation date and user id
                factDTO.setCreationDate(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                factDTO.setUserId(funeralDTO.getUserId());

                factService.createNewFact(factDTO);
            }
        }
    }
}
