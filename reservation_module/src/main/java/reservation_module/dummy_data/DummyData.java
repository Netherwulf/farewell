package reservation_module.dummy_data;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import reservation_module.models.Deceased;
import reservation_module.models.Funeral;
import reservation_module.models.Grave;
import reservation_module.repositories.DeceasedRepository;
import reservation_module.repositories.FuneralRepository;
import reservation_module.repositories.GraveRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class DummyData implements ApplicationListener<ContextRefreshedEvent> {

    private FuneralRepository funeralRepository;
    private GraveRepository graveRepository;
    private DeceasedRepository deceasedRepository;

    public DummyData(FuneralRepository funeralRepository, GraveRepository graveRepository, DeceasedRepository deceasedRepository) {
        this.funeralRepository = funeralRepository;
        this.graveRepository = graveRepository;
        this.deceasedRepository = deceasedRepository;
    }

    private void loadData() {
        // initialization of funerals list
        List<Funeral> funeralList = new ArrayList<>();

        // initialization of graves list
        List<Grave> graveList = new ArrayList<>();

        // create first funeral
        Funeral funeralOne = new Funeral();
        funeralOne.setReservationDate("2012-05-03 22:15:10-00");
        funeralOne.setDate("2012-06-12 10:15:00-00");
        funeralOne.setUserId("25");
        funeralOne.setFuneralDirectorId("30");

        // create grave for first funeral
        Grave graveOne = new Grave();
        graveOne.setReservationDate("2016-06-22 19:10:25-00");
        graveOne.setGraveNumber("99996");
        graveOne.setCoordinates("41-24-12.2-N 2-10-26.5-E");
        graveOne.setCapacity("4");

        funeralOne.setGrave(graveOne);
        graveOne.setFuneral(funeralOne);

        // create first deceased for first grave
        Deceased graveOneDeceasedOne = new Deceased();
        graveOneDeceasedOne.setSurname("Kowalski");
        graveOneDeceasedOne.setName("Jan");
        graveOneDeceasedOne.setDateOfBirth("1970-05-03");
        graveOneDeceasedOne.setPlaceOfBirth("Warszawa");
        graveOneDeceasedOne.setDateOfDeath("2012-01-30");
        graveOneDeceasedOne.setPlaceOfDeath("Gdynia");

        graveOne.addDeceased(graveOneDeceasedOne);

        // create second deceased for first grave
        Deceased graveOneDeceasedTwo = new Deceased();
        graveOneDeceasedTwo.setSurname("Nowak");
        graveOneDeceasedTwo.setName("Rajmund");
        graveOneDeceasedTwo.setDateOfBirth("1982-12-30");
        graveOneDeceasedTwo.setPlaceOfBirth("Nowa Sól");
        graveOneDeceasedTwo.setDateOfDeath("2001-01-02");
        graveOneDeceasedTwo.setPlaceOfDeath("Wrocław");

        graveOne.addDeceased(graveOneDeceasedTwo);

        // save first funeral
        funeralList.add(funeralOne);

        // create second funeral
        Funeral funeralTwo = new Funeral();
        funeralTwo.setReservationDate("2007-07-17 23:22:31-00");
        funeralTwo.setDate("2007-08-23 12:45:00-00");
        funeralTwo.setUserId("26");
        funeralTwo.setFuneralDirectorId("31");

        // create grave for second funeral
        Grave graveTwo = new Grave();
        graveTwo.setReservationDate("2003-10-08 11:10:48-00");
        graveTwo.setGraveNumber("99997");
        graveTwo.setCoordinates("17-18-19.7-S 19-59-37.4-W");
        graveTwo.setCapacity("3");

        funeralTwo.setGrave(graveTwo);
        graveTwo.setFuneral(funeralTwo);

        // create first deceased for first grave
        Deceased graveTwoDeceasedOne = new Deceased();
        graveTwoDeceasedOne.setSurname("Bałkański");
        graveTwoDeceasedOne.setName("Patryk");
        graveTwoDeceasedOne.setDateOfBirth("1988-09-13");
        graveTwoDeceasedOne.setPlaceOfBirth("Dźwirzyno");
        graveTwoDeceasedOne.setDateOfDeath("2010-03-08");
        graveTwoDeceasedOne.setPlaceOfDeath("Berlin");

        graveTwo.addDeceased(graveTwoDeceasedOne);

        // save second funeral
        funeralList.add(funeralTwo);

        // create third funeral with no graves
        Funeral funeralThree = new Funeral();
        funeralThree.setReservationDate("2014-05-13 17:29:39-00");
        funeralThree.setDate("2014-07-22 15:30:00-00");
        funeralThree.setUserId("27");
        funeralThree.setFuneralDirectorId("32");

        // save third funeral
        funeralList.add(funeralThree);

        // create grave with no funeral and no deceased
        Grave graveThree = new Grave();
        graveThree.setReservationDate("2002-12-21 05:47:30-00");
        graveThree.setGraveNumber("99998");
        graveThree.setCoordinates("30-22-16.0-N 3-25-27.7-W");
        graveThree.setCapacity("2");

        // save grave with no funeral and no deceased
        graveList.add(graveThree);

        // saving funerals and single grave to database
        funeralRepository.saveAll(funeralList);
        graveRepository.saveAll(graveList);

        // print number of funerals, graves and deceased saved to database
        System.out.println("Funerals loaded = " + funeralRepository.count() + " / 3");
        System.out.println("Graves loaded   = " + graveRepository.count() + " / 3");
        System.out.println("Deceased loaded = " + deceasedRepository.count() + " / 3");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadData();
    }
}
