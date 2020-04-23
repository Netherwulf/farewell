package analytical_module.dummy_data;

import analytical_module.models.Fact;
import analytical_module.models.FuneralDirector;
import analytical_module.repositories.FactRepository;
import analytical_module.repositories.FuneralDirectorRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DummyData implements ApplicationListener<ContextRefreshedEvent> {

    private FactRepository factRepository;
    private FuneralDirectorRepository funeralDirectorRepository;

    public DummyData(FactRepository factRepository, FuneralDirectorRepository funeralDirectorRepository) {
        this.factRepository = factRepository;
        this.funeralDirectorRepository = funeralDirectorRepository;
    }

    private void loadData() {
        // initialization of facts list
        List<Fact> factList = new ArrayList<>();

        // initialization of funeral directors list
        List<FuneralDirector> funeralDirectorList = new ArrayList<>();

        // create first fact

        // saving funeral directors and single facts to database
        factRepository.saveAll(factList);
        funeralDirectorRepository.saveAll(funeralDirectorList);

        // print number of funeral directors and facts saved to database
        System.out.println("Facts loaded = " + factRepository.count() + " / 3");
        System.out.println("Funeral Directors loaded   = " + funeralDirectorRepository.count() + " / 3");

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadData();
    }
}
