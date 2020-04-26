package analytical_module.dummy_data;

import analytical_module.models.Fact;
import analytical_module.models.FuneralDirector;
import analytical_module.repositories.FactRepository;
import analytical_module.repositories.FuneralDirectorRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DummyData implements ApplicationListener<ContextRefreshedEvent> {

    private final FactRepository factRepository;
    private final FuneralDirectorRepository funeralDirectorRepository;

    public DummyData(FactRepository factRepository, FuneralDirectorRepository funeralDirectorRepository) {
        this.factRepository = factRepository;
        this.funeralDirectorRepository = funeralDirectorRepository;
    }

    private FuneralDirector createFuneralDirector(int funeralDirectorIndex, List<String> surnames, List<String> names,
                                                  List<String> religions, List<String> emails) {
        FuneralDirector funeralDirector = new FuneralDirector();

        funeralDirector.setSurname(surnames.get(funeralDirectorIndex));
        funeralDirector.setName(names.get(funeralDirectorIndex));
        funeralDirector.setDateOfBirth((new Integer(1970 + funeralDirectorIndex)).toString() + "-0"
                + (new Integer(1 + funeralDirectorIndex)).toString() + "-"
                + (new Integer(10 + funeralDirectorIndex)).toString());
        funeralDirector.setReligion(religions.get(funeralDirectorIndex % 7));
        funeralDirector.setEmail((names.get(funeralDirectorIndex)).toLowerCase() + emails.get(funeralDirectorIndex % 6));

        return funeralDirector;
    }

    private Fact createFact(int factIndex, List<String> surnames, List<String> names, List<String> cities, Random r) {
        Fact fact = new Fact();

        fact.setFuneralId(Integer.toString(factIndex + 900));
        fact.setFuneralReservationDate(2020 + "-0" + ((1 + factIndex) % 8) + "-" + (10 + factIndex));
        fact.setFuneralPurchaseDate(2020 + "-0" + (((1 + factIndex) % 8) + 1) + "-" + (11 + factIndex));
        fact.setFuneralDate(2020 + "-0" + (((1 + factIndex) % 8) + 2) + "-" + (11 + factIndex));

        fact.setGraveId(Integer.toString(factIndex + 1000));
        fact.setGraveReservationDate(2019 + "-0" + ((1 + factIndex) % 8) + "-" + (10 + factIndex));
        fact.setGravePurchaseDate(2019 + "-0" + (((1 + factIndex) % 8) + 1) + "-" + (11 + factIndex));
        fact.setGraveNumber(Integer.toString(factIndex + 50));
        fact.setGraveCoordinates((39 + (factIndex % 5)) + "-" + (20 + (factIndex % 10)) + "-" + (10 + (factIndex % 5)) +
                "." + ((1 + factIndex) % 5) + "-N " + ((1 + factIndex) % 6) + "-" + ((2 + factIndex) % 12) + "-" +
                (22 + (factIndex % 8)) + "." + ((1 + factIndex) % 5) + "-E");
        fact.setGraveCapacity(Integer.toString(2 + r.nextInt(10)));

        fact.setDeceasedId(Integer.toString(factIndex + 1100));
        fact.setDeceasedSurname(surnames.get((factIndex + 1) % surnames.size()));
        fact.setDeceasedName(names.get((factIndex + 1) % names.size()));
        fact.setDeceasedDateOfBirth((1950 + factIndex) + "-0" + (1 + factIndex) + "-" + (10 + factIndex));
        fact.setDeceasedPlaceOfBirth(cities.get(1 + (factIndex % 7)));
        fact.setDeceasedDateOfDeath((2001 + r.nextInt(factIndex + 1)) + "-0" + (1 + factIndex) + "-" + (10 + factIndex));
        fact.setDeceasedPlaceOfDeath(cities.get((3 + r.nextInt(factIndex + 1)) % 8));

        fact.setCreationDate(2018 + "-0" + ((1 + factIndex) % 8) + "-" + (10 + factIndex));
        fact.setUserId(Integer.toString( 40 + r.nextInt(40 + factIndex)));

        return fact;
    }

    private void loadData() {
        // list of names
        List<String> names = new ArrayList<>(Arrays.asList("Jan", "Paweł", "Jacek", "Sławomir", "Piotr",
                "Andrzej", "Jarosław", "Jakub", "Rafał"));

        // list of surnames
        List<String> surnames = new ArrayList<>(Arrays.asList("Kowalski", "Nowak", "Marecki", "Janecki", "Piotrowski",
                "Jakubowicz", "Andrzejewski", "Pawelski", "Sławomirski"));

        // list of emails
        List<String> emails = new ArrayList<>(Arrays.asList("@gmail.com", "@yahoo.com", "@pwr.com", "o2.pl",
                "tlen.pl", "wp.pl"));

        // list of religions
        List<String> religions = new ArrayList<>(Arrays.asList("catholicism", "atheism", "protestantism", "islam",
                "judaism", "confucianism", "shinto"));

        // list of cities
        List<String> cities = new ArrayList<>(Arrays.asList("Boston", "Berlin", "Nowy Jork", "Warszawa", "Wrocław",
                "Poznań", "Moskwa", "Sztokholm"));

        // initialization of facts list
        List<Fact> factList = new ArrayList<>();

        // initialization of funeral directors list
        List<FuneralDirector> funeralDirectorList = new ArrayList<>();

        Random r = new Random();

        // create facts
        for (int funeralDirectorIndex = 0; funeralDirectorIndex < 3; funeralDirectorIndex++) {
            FuneralDirector funeralDirector = createFuneralDirector(funeralDirectorIndex, surnames, names, religions, emails);

            Set<Fact> factSet = new HashSet<>();

            int factsCount = r.nextInt(2) + 1;

            for (int factIndex = 0; factIndex < factsCount; factIndex++) {
                Fact fact = createFact(factList.size(), surnames, names, cities, r);

                fact.setFuneralDirector(funeralDirector);

                factSet.add(fact);
                factList.add(fact);
            }

            funeralDirector.setFacts(factSet);

            funeralDirectorList.add(funeralDirector);
        }

        // create funeral directors without facts
        for (int funeralDirectorIndex = 3; funeralDirectorIndex < 6; funeralDirectorIndex++) {
            FuneralDirector funeralDirector = createFuneralDirector(funeralDirectorIndex, surnames, names, religions, emails);

            funeralDirector.setFacts(null);

            funeralDirectorList.add(funeralDirector);
        }

        // create facts without funeral directors
        for (int factIndex = 6; factIndex < 10; factIndex++) {
            Fact fact = createFact(factList.size(), surnames, names, cities, r);

            fact.setFuneralDirector(null);

            factList.add(fact);
        }

        // saving funeral directors and single facts to database
        factRepository.saveAll(factList);
        funeralDirectorRepository.saveAll(funeralDirectorList);

        // print number of funeral directors and facts saved to database
        System.out.println("Facts loaded = " + factRepository.count() + " / 6-12");
        System.out.println("Funeral Directors loaded   = " + funeralDirectorRepository.count() + " / 5");

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadData();
    }
}
