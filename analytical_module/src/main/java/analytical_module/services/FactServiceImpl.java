package analytical_module.services;

import analytical_module.api.v1.mapper.FactMapper;
import analytical_module.api.v1.mapper.FuneralDirectorMapper;
import analytical_module.api.v1.model.FactDTO;
import analytical_module.api.v1.model.FactListDTO;
import analytical_module.api.v1.model.FuneralReportDTO;
import analytical_module.api.v1.model.GraveReportDTO;
import analytical_module.models.Fact;
import analytical_module.models.FuneralDirector;
import analytical_module.repositories.FactRepository;
import analytical_module.repositories.FuneralDirectorRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import javax.transaction.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Slf4j
@Service
@Transactional
public class FactServiceImpl implements FactService {

    private final FactMapper factMapper;
    private final FuneralDirectorMapper funeralDirectorMapper;
    private final FactRepository factRepository;
    private final FuneralDirectorRepository funeralDirectorRepository;

    public FactServiceImpl(FactMapper factMapper, FuneralDirectorMapper funeralDirectorMapper, FactRepository factRepository, FuneralDirectorRepository funeralDirectorRepository) {
        this.factMapper = factMapper;
        this.funeralDirectorMapper = funeralDirectorMapper;
        this.factRepository = factRepository;
        this.funeralDirectorRepository = funeralDirectorRepository;
    }

    @Override
    public FactListDTO getAllFacts() {
        List<FactDTO> factDTOs = factRepository
                .findAll()
                .stream()
                .map(fact -> {
                    FactDTO factDTO = factMapper.factToFactDTO(fact);
                    if (fact.getFuneralDirector() != null) {
                        factDTO.setFuneralDirectorId(fact.getFuneralDirector().getId());
                    }
                    return factDTO;
                })
                .collect(Collectors.toList());
        return new FactListDTO(factDTOs);
    }

    @Override
    public ResponseEntity getFactsByFuneralDirectorId(Long funeralDirectorId) {
        Optional<FuneralDirector> funeralDirectorOptional = funeralDirectorRepository.findById(funeralDirectorId);

        if (!funeralDirectorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funeral Director not found for ID: " + funeralDirectorId.toString());
        }

        FuneralDirector funeralDirector = funeralDirectorOptional.get();

        if (funeralDirector.getFacts() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funeral Director with ID: " + funeralDirectorId.toString() + " does not have any facts.");
        }

        List<FactDTO> factDTOs = funeralDirector
                .getFacts()
                .stream()
                .map(fact -> {
                    FactDTO factDTO = factMapper.factToFactDTO(fact);
                    factDTO.setFuneralDirectorId(funeralDirectorId);

                    return factDTO;
                })
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(new FactListDTO(factDTOs));
    }

    @Override
    public ResponseEntity getFactById(Long factId) {
        Optional<Fact> factOptional = factRepository.findById(factId);

        if (!factOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fact not found for ID: " + factId.toString());
        }

        Fact fact = factOptional.get();

        FactDTO factDTO = factMapper.factToFactDTO(fact);

        if (fact.getFuneralDirector() != null) {
            factDTO.setFuneralDirectorId(fact.getFuneralDirector().getId());
        }

        return ResponseEntity.status(HttpStatus.OK).body(factDTO);
    }

    @Override
    public FactDTO createNewFact(FactDTO factDTO) {
        return saveAndReturnDTO(factDTO);
    }

    @Override
    @Transactional
    public FactDTO saveAndReturnDTO(FactDTO factDTO) {
        Fact fact = factMapper.factDTOToFact(factDTO);

        Fact savedFact = new Fact();

        if (factDTO.getFuneralDirectorId() != null) {
            Optional<FuneralDirector> funeralDirectorOptional = funeralDirectorRepository.findById(factDTO.getFuneralDirectorId());

            if (funeralDirectorOptional.isPresent()) {
                FuneralDirector funeralDirector = funeralDirectorOptional.get();
                fact.setFuneralDirector(funeralDirector);
                funeralDirector.getFacts().add(fact);

                FuneralDirector savedFuneralDirector = funeralDirectorRepository.save(funeralDirector);

            } else {
                savedFact = factRepository.save(fact);
            }
        } else {
            savedFact = factRepository.save(fact);
        }

        FactDTO returnDTO = factMapper.factToFactDTO(savedFact);

        if (savedFact.getFuneralDirector() != null) {
            returnDTO.setFuneralDirectorId(savedFact.getFuneralDirector().getId());
        }

        return returnDTO;
    }

    private HashMap<String, Long> getFuneralCountsPerDateLength(int dateLength) {
        FactListDTO factDTOList = getAllFacts();

        HashMap<String, Long> funeralsCountByDateLength = new HashMap<>();
        List<String> funeralIds = new ArrayList<>();

        for (FactDTO factDTO: factDTOList.getFacts()) {
            if (factDTO.getFuneralId() != null && factDTO.getFuneralDate() != null) {
                String funeralDate = factDTO.getFuneralDate().substring(0, dateLength); // 10-days, 7-months, 4-years
                if (!funeralIds.contains(factDTO.getFuneralId())) {
                    if (!funeralsCountByDateLength.containsKey(funeralDate)) {
                        funeralsCountByDateLength.put(funeralDate, 1L);
                        funeralIds.add(factDTO.getFuneralId());
                    } else {
                        Long currentCount = funeralsCountByDateLength.get(funeralDate);
                        funeralsCountByDateLength.replace(funeralDate, currentCount + 1L);
                        funeralIds.add(factDTO.getFuneralId());
                    }
                }
            }
        }

//        factRepository
//                .findAll()
//                .stream()
//                .map(fact -> {
//                    if (fact.getFuneralId() != null && fact.getFuneralDate() != null) {
//                        String funeralDate = fact.getFuneralDate().substring(0, dateLength); // 10-days, 7-months, 4-years
//                        if (!funeralIds.contains(fact.getFuneralId())) {
//                            if (!funeralsCountByDateLength.containsKey(funeralDate)) {
//                                funeralsCountByDateLength.put(funeralDate, 1L);
//                                funeralIds.add(fact.getFuneralId());
//                            } else {
//                                Long currentCount = funeralsCountByDateLength.get(funeralDate);
//                                funeralsCountByDateLength.replace(funeralDate, currentCount + 1L);
//                                funeralIds.add(fact.getFuneralId());
//                            }
//                        }
//                    }
//                    return null;
//                });

        return funeralsCountByDateLength;
    }

    private Double getAverage(HashMap<String, Long> funeralsCountByDateLength) {
        Double averageFuneralsPerDateLength = funeralsCountByDateLength
                .values()
                .stream()
                .mapToDouble(Long::doubleValue)
                .average()
                .orElse(0);

        averageFuneralsPerDateLength = new BigDecimal(averageFuneralsPerDateLength).setScale(3, RoundingMode.HALF_UP).doubleValue();

        return averageFuneralsPerDateLength;
    }

    private Double getMedian(HashMap<String, Long> funeralsCountByDateLength) {
        if (funeralsCountByDateLength.size() == 0) {
            return (double) 0;
        }

        if (funeralsCountByDateLength.size() == 1) {
            return new Double(funeralsCountByDateLength.get(new ArrayList(funeralsCountByDateLength.keySet()).get(0)));
        }

        DoubleStream funeralsPerDateLengthSorted = funeralsCountByDateLength
                .values()
                .stream()
                .mapToDouble(Long::doubleValue)
                .sorted();

        Double medianFuneralsPerDateLength = funeralsCountByDateLength.size()%2 == 0?
                funeralsPerDateLengthSorted.skip(funeralsCountByDateLength.size()/2-1).limit(2).average().getAsDouble():
                funeralsPerDateLengthSorted.skip(funeralsCountByDateLength.size()/2).findFirst().getAsDouble();

        medianFuneralsPerDateLength = new BigDecimal(medianFuneralsPerDateLength).setScale(3, RoundingMode.HALF_UP).doubleValue();

        return medianFuneralsPerDateLength;
    }

    private Long getMode(HashMap<String, Long> funeralsCountByDateLength) {
        long mode = 1L;
        int maxCount = 0;
        List<Long> checkedValues = new ArrayList<>();
        for (Long numOfFunerals: funeralsCountByDateLength.values()) {
            if (!checkedValues.contains(numOfFunerals)) {
                checkedValues.add(numOfFunerals);
                int currentCount = 0;
                for (Long funeralsCountElem : funeralsCountByDateLength.values()) {
                    if (numOfFunerals.equals(funeralsCountElem)) {
                        ++currentCount;
                    }
                }
                if (currentCount > maxCount) {
                    maxCount = currentCount;
                    mode = numOfFunerals;
                }
            }
        }

        return mode;
    }

    private HashMap<String, Long> getFuneralsPerFuneralDirector() {
        HashMap<String, Long> funeralsPerFuneralDirector = new HashMap<>();
        List<String> funeralIds = new ArrayList<>();

        FactListDTO factDTOList = getAllFacts();

        for (FactDTO factDTO: factDTOList.getFacts()) {
            if (factDTO.getFuneralId() != null && factDTO.getFuneralDirectorId() != null) {
                String funeralDirectorId = factDTO.getFuneralDirectorId().toString();
                if (!funeralIds.contains(factDTO.getFuneralId())) {
                    if (!funeralsPerFuneralDirector.containsKey(funeralDirectorId)) {
                        funeralsPerFuneralDirector.put(funeralDirectorId, 1L);
                        funeralIds.add(factDTO.getFuneralId());
                    } else {
                        Long currentCount = funeralsPerFuneralDirector.get(funeralDirectorId);
                        funeralsPerFuneralDirector.replace(funeralDirectorId, currentCount + 1L);
                        funeralIds.add(factDTO.getFuneralId());
                    }
                }
            }
        }

//        factRepository
//                .findAll()
//                .stream()
//                .map(fact -> {
//                    if (fact.getFuneralId() != null && fact.getFuneralDirector() != null) {
//                        String funeralDirectorId = fact.getFuneralDirector().getId().toString();
//                        if (!funeralIds.contains(fact.getFuneralId())) {
//                            if (!funeralsPerFuneralDirector.containsKey(funeralDirectorId)) {
//                                funeralsPerFuneralDirector.put(funeralDirectorId, 1L);
//                                funeralIds.add(fact.getFuneralId());
//                            } else {
//                                Long currentCount = funeralsPerFuneralDirector.get(funeralDirectorId);
//                                funeralsPerFuneralDirector.replace(funeralDirectorId, currentCount + 1L);
//                                funeralIds.add(fact.getFuneralId());
//                            }
//                        }
//                    }
//                    return null;
//                });

        return funeralsPerFuneralDirector;
    }

    private HashMap<String, Long> getFuneralsPerUser() {
        HashMap<String, Long> funeralsPerUser = new HashMap<>();
        List<String> funeralIds = new ArrayList<>();

        FactListDTO factDTOList = getAllFacts();

        for (FactDTO factDTO: factDTOList.getFacts()) {
            String userId = factDTO.getUserId().toString();
            if (factDTO.getFuneralId() != null && !funeralIds.contains(factDTO.getFuneralId())) {
                if (!funeralsPerUser.containsKey(userId)) {
                    funeralsPerUser.put(userId, 1L);
                    funeralIds.add(factDTO.getFuneralId());
                } else {
                    Long currentCount = funeralsPerUser.get(userId);
                    funeralsPerUser.replace(userId, currentCount + 1L);
                    funeralIds.add(factDTO.getFuneralId());
                }
            }
        }

//        factRepository
//                .findAll()
//                .stream()
//                .map(fact -> {
//                    String userId = fact.getUserId().toString();
//                    if (fact.getFuneralId() != null && !funeralIds.contains(fact.getFuneralId())) {
//                        if (!funeralsPerUser.containsKey(userId)) {
//                            funeralsPerUser.put(userId, 1L);
//                            funeralIds.add(fact.getFuneralId());
//                        } else {
//                            Long currentCount = funeralsPerUser.get(userId);
//                            funeralsPerUser.replace(userId, currentCount + 1L);
//                            funeralIds.add(fact.getFuneralId());
//                        }
//                    }
//                    return null;
//                });

        return funeralsPerUser;
    }

    private HashMap<String, Long> getGraveCountsPerDateLength(int dateLength) {
        HashMap<String, Long> gravesCountByDateLength = new HashMap<>();
        List<String> graveIds = new ArrayList<>();

        FactListDTO factDTOList = getAllFacts();

        for (FactDTO factDTO: factDTOList.getFacts()) {
            if (factDTO.getGraveId() != null && factDTO.getGraveReservationDate() != null) {
                String graveDate = factDTO.getGraveReservationDate().substring(0, dateLength); // 10-days, 7-months, 4-years
                if (!graveIds.contains(factDTO.getGraveId())) {
                    if (!gravesCountByDateLength.containsKey(graveDate)) {
                        gravesCountByDateLength.put(graveDate, 1L);
                        graveIds.add(factDTO.getGraveId());
                    } else {
                        Long currentCount = gravesCountByDateLength.get(graveDate);
                        gravesCountByDateLength.replace(graveDate, currentCount + 1L);
                        graveIds.add(factDTO.getGraveId());
                    }
                }
            }
        }

//        factRepository
//                .findAll()
//                .stream()
//                .map(fact -> {
//                    if (fact.getGraveId() != null && fact.getGraveReservationDate() != null) {
//                        String graveDate = fact.getGraveReservationDate().substring(0, dateLength); // 10-days, 7-months, 4-years
//                        if (!graveIds.contains(fact.getGraveId())) {
//                            if (!gravesCountByDateLength.containsKey(graveDate)) {
//                                gravesCountByDateLength.put(graveDate, 1L);
//                                graveIds.add(fact.getGraveId());
//                            } else {
//                                Long currentCount = gravesCountByDateLength.get(graveDate);
//                                gravesCountByDateLength.replace(graveDate, currentCount + 1L);
//                                graveIds.add(fact.getGraveId());
//                            }
//                        }
//                    }
//                    return null;
//                });

        return gravesCountByDateLength;
    }

    private HashMap<String, Long> getDeceasedPerGrave() {
        HashMap<String, Long> deceasedPerGrave = new HashMap<>();
        List<String> deceasedIds = new ArrayList<>();

        FactListDTO factDTOList = getAllFacts();

        for (FactDTO factDTO: factDTOList.getFacts()) {
            String graveId = factDTO.getGraveId();
            if (factDTO.getDeceasedId() != null && !deceasedIds.contains(factDTO.getDeceasedId())) {
                if (!deceasedPerGrave.containsKey(graveId)) {
                    deceasedPerGrave.put(graveId, 1L);
                    deceasedIds.add(factDTO.getDeceasedId());
                } else {
                    Long currentCount = deceasedPerGrave.get(graveId);
                    deceasedPerGrave.replace(graveId, currentCount + 1L);
                    deceasedIds.add(factDTO.getDeceasedId());
                }
            }
        }

//        factRepository
//                .findAll()
//                .stream()
//                .map(fact -> {
//                    String graveId = fact.getGraveId().toString();
//                    if (fact.getDeceasedId() != null && !deceasedIds.contains(fact.getDeceasedId())) {
//                        if (!deceasedPerGrave.containsKey(graveId)) {
//                            deceasedPerGrave.put(graveId, 1L);
//                            deceasedIds.add(fact.getDeceasedId());
//                        } else {
//                            Long currentCount = deceasedPerGrave.get(graveId);
//                            deceasedPerGrave.replace(graveId, currentCount + 1L);
//                            deceasedIds.add(fact.getDeceasedId());
//                        }
//                    }
//                    return null;
//                });

        return deceasedPerGrave;
    }

    private HashMap<String, Long> getGravesPerUser() {
        HashMap<String, Long> gravesPerUser = new HashMap<>();
        List<String> graveIds = new ArrayList<>();

        FactListDTO factDTOList = getAllFacts();

        for (FactDTO factDTO: factDTOList.getFacts()) {
            String userId = factDTO.getUserId().toString();
            if (factDTO.getGraveId() != null && !graveIds.contains(factDTO.getGraveId())) {
                if (!gravesPerUser.containsKey(userId)) {
                    gravesPerUser.put(userId, 1L);
                    graveIds.add(factDTO.getGraveId());
                } else {
                    Long currentCount = gravesPerUser.get(userId);
                    gravesPerUser.replace(userId, currentCount + 1L);
                    graveIds.add(factDTO.getGraveId());
                }
            }
        }

//        factRepository
//                .findAll()
//                .stream()
//                .map(fact -> {
//                    String userId = fact.getUserId().toString();
//                    if (fact.getGraveId() != null && !graveIds.contains(fact.getGraveId())) {
//                        if (!gravesPerUser.containsKey(userId)) {
//                            gravesPerUser.put(userId, 1L);
//                            graveIds.add(fact.getGraveId());
//                        } else {
//                            Long currentCount = gravesPerUser.get(userId);
//                            gravesPerUser.replace(userId, currentCount + 1L);
//                            graveIds.add(fact.getGraveId());
//                        }
//                    }
//                    return null;
//                });

        return gravesPerUser;
    }

    @Override
    public ResponseEntity getFuneralReport() {
        FuneralReportDTO funeralReportDTO = new FuneralReportDTO();

        // average time between reservation and purchase in days
        HashMap<String, Date> funeralsReservationDates = new HashMap<>();
        HashMap<String, Date> funeralsPurchaseDates = new HashMap<>();
        HashMap<String, Long> funeralsReservationToPurchaseTime = new HashMap<>();

        FactListDTO factDTOList = getAllFacts();

        for (FactDTO factDTO: factDTOList.getFacts()) {
            if (!funeralsReservationDates.containsKey(factDTO.getFuneralId()) && factDTO.getFuneralReservationDate() != null) {
                Date reservationDate = new Date();
                try {
                    reservationDate = new SimpleDateFormat("yyyy-MM-dd").parse(factDTO.getFuneralReservationDate().substring(0, 10));
                } catch (Exception e) {
                    System.out.println("Exception occurred during date parsing date: " + factDTO.getFuneralReservationDate().substring(0, 10));
                }
                funeralsReservationDates.put(factDTO.getFuneralId(), reservationDate);
            }
            if (!funeralsPurchaseDates.containsKey(factDTO.getFuneralId()) && factDTO.getFuneralPurchaseDate() != null) {
                Date purchaseDate = new Date();
                try {
                    purchaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(factDTO.getFuneralPurchaseDate().substring(0, 10));
                } catch (Exception e) {
                    System.out.println("Exception occurred during date parsing date: " + factDTO.getFuneralReservationDate().substring(0, 10));
                }
                funeralsPurchaseDates.put(factDTO.getFuneralId(), purchaseDate);
            }
        }

//        factRepository.findAll().stream()
//                .map(fact -> {
//                    if (!funeralsReservationDates.containsKey(fact.getFuneralId()) && fact.getFuneralReservationDate() != null) {
//                        Date reservationDate = new Date();
//                        try {
//                            reservationDate = new SimpleDateFormat("yyyy-MM-dd").parse(fact.getFuneralReservationDate().substring(0, 10));
//                        } catch (Exception e) {
//                            System.out.println("Exception occurred during date parsing date: " + fact.getFuneralReservationDate().substring(0, 10));
//                        }
//                        funeralsReservationDates.put(fact.getFuneralId(), reservationDate);
//                    }
//                    if (!funeralsPurchaseDates.containsKey(fact.getFuneralId()) && fact.getFuneralPurchaseDate() != null) {
//                        Date purchaseDate = new Date();
//                        try {
//                        purchaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(fact.getFuneralPurchaseDate().substring(0, 10));
//                        } catch (Exception e) {
//                            System.out.println("Exception occurred during date parsing date: " + fact.getFuneralReservationDate().substring(0, 10));
//                        }
//                        funeralsPurchaseDates.put(fact.getFuneralId(), purchaseDate);
//                    }
//                    return null;
//                });

        for (String id: funeralsReservationDates.keySet()) {
            if (funeralsPurchaseDates.containsKey(id)) {
                LocalDate reservationDate = funeralsReservationDates
                        .get(id).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                LocalDate purchaseDate = funeralsPurchaseDates
                        .get(id).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                funeralsReservationToPurchaseTime.put(id, ChronoUnit.DAYS.between(reservationDate, purchaseDate));
            }
        }

        Double averageReservationToPurchaseTime = funeralsReservationToPurchaseTime
                .values()
                .stream()
                .mapToDouble(Long::doubleValue)
                .average()
                .orElse(0);

        averageReservationToPurchaseTime = new BigDecimal(averageReservationToPurchaseTime).setScale(3, RoundingMode.HALF_UP).doubleValue();

        funeralReportDTO.setAverageReservationToPurchaseTime(averageReservationToPurchaseTime);

        // average number of funerals per day
        HashMap<String, Long> funeralsCountByDays = getFuneralCountsPerDateLength(10);

        Double averageFuneralsPerDay = getAverage(funeralsCountByDays);

        funeralReportDTO.setAverageFuneralsPerDay(averageFuneralsPerDay);

        // median of number of funerals per day
        Double medianFuneralsPerDay = getMedian(funeralsCountByDays);

        funeralReportDTO.setMedianFuneralsPerDay(medianFuneralsPerDay);

        // mode of number of funerals per day
        long mode = getMode(funeralsCountByDays);

        funeralReportDTO.setModeFuneralsPerDay(mode);

        // average number of funerals per month
        HashMap<String, Long> funeralsCountByMonths = getFuneralCountsPerDateLength(7);

        Double averageFuneralsPerMonth = getAverage(funeralsCountByMonths);

        funeralReportDTO.setAverageFuneralsPerMonth(averageFuneralsPerMonth);

        // median of number of funerals per month
        Double medianFuneralsPerMonth = getMedian(funeralsCountByMonths);

        funeralReportDTO.setMedianFuneralsPerMonth(medianFuneralsPerMonth);

        // mode of number of funerals per month
        mode = getMode(funeralsCountByMonths);

        funeralReportDTO.setModeFuneralsPerMonth(mode);

        // average number of funerals per year
        HashMap<String, Long> funeralsCountByYears = getFuneralCountsPerDateLength(4);

        Double averageFuneralsPerYear = getAverage(funeralsCountByYears);

        funeralReportDTO.setAverageFuneralsPerYear(averageFuneralsPerYear);

        // median of number of funerals per year
        Double medianFuneralsPerYear = getMedian(funeralsCountByYears);

        funeralReportDTO.setMedianFuneralsPerYear(medianFuneralsPerYear);

        // mode of number of funerals per year
        mode = getMode(funeralsCountByYears);

        funeralReportDTO.setModeFuneralsPerYear(mode);

        // number of funerals per Funeral Director - HashMap
        HashMap<String, Long> funeralsPerFuneralDirector = getFuneralsPerFuneralDirector();

        funeralReportDTO.setFuneralsPerFuneralDirector(funeralsPerFuneralDirector);

        // average number of funerals per Funeral Director
        Double averageFuneralsPerFuneralDirector = getAverage(funeralsPerFuneralDirector);

        funeralReportDTO.setAverageFuneralsPerFuneralDirector(averageFuneralsPerFuneralDirector);

        // median of number of funerals per Funeral Director
        Double medianFuneralsPerFuneralDirector = getMedian(funeralsPerFuneralDirector);

        funeralReportDTO.setMedianFuneralsPerFuneralDirector(medianFuneralsPerFuneralDirector);

        // mode of number of funerals per Funeral Director
        Long modeFuneralsPerFuneralDirector = getMode(funeralsPerFuneralDirector);

        funeralReportDTO.setModeFuneralsPerFuneralDirector(modeFuneralsPerFuneralDirector);

        // number of funerals per User - HashMap
        HashMap<String, Long> funeralsPerUser = getFuneralsPerUser();

        funeralReportDTO.setFuneralsPerUser(funeralsPerUser);

        // average number of funerals per User
        Double averageFuneralsPerUser = getAverage(funeralsPerUser);

        funeralReportDTO.setAverageFuneralsPerUser(averageFuneralsPerUser);

        // median of number of funerals per User
        Double medianFuneralsPerUser = getMedian(funeralsPerUser);

        funeralReportDTO.setMedianFuneralsPerUser(medianFuneralsPerUser);

        // mode of number of funerals per User
        Long modeFuneralsPerUser = getMode(funeralsPerUser);

        funeralReportDTO.setModeFuneralsPerUser(modeFuneralsPerUser);

        return ResponseEntity.status(HttpStatus.OK).body(funeralReportDTO);
    }

    @Override
    public GraveReportDTO getGraveReport() {
        GraveReportDTO graveReportDTO = new GraveReportDTO();

        // average time between reservation and purchase in days
        HashMap<String, Date> gravesReservationDates = new HashMap<>();
        HashMap<String, Date> gravesPurchaseDates = new HashMap<>();
        HashMap<String, Long> gravesReservationToPurchaseTime = new HashMap<>();

        FactListDTO factDTOList = getAllFacts();

        for (FactDTO factDTO: factDTOList.getFacts()) {
            if (!gravesReservationDates.containsKey(factDTO.getGraveId()) && factDTO.getGraveReservationDate() != null) {
                Date reservationDate = new Date();
                try {
                    reservationDate = new SimpleDateFormat("yyyy-MM-dd").parse(factDTO.getGraveReservationDate().substring(0, 10));
                } catch (Exception e) {
                    System.out.println("Exception occurred during date parsing date: " + factDTO.getFuneralReservationDate().substring(0, 10));
                }
                gravesReservationDates.put(factDTO.getGraveId(), reservationDate);
            }
            if (!gravesPurchaseDates.containsKey(factDTO.getGraveId()) && factDTO.getGravePurchaseDate() != null) {
                Date purchaseDate = new Date();
                try {
                    purchaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(factDTO.getGravePurchaseDate().substring(0, 10));
                } catch (Exception e) {
                    System.out.println("Exception occurred during date parsing date: " + factDTO.getFuneralReservationDate().substring(0, 10));
                }
                gravesPurchaseDates.put(factDTO.getGraveId(), purchaseDate);
            }
        }

//        factRepository.findAll().stream()
//                .map(fact -> {
//                    if (!gravesReservationDates.containsKey(fact.getGraveId()) && fact.getGraveReservationDate() != null) {
//                        Date reservationDate = new Date();
//                        try {
//                        reservationDate = new SimpleDateFormat("yyyy-MM-dd").parse(fact.getGraveReservationDate().substring(0, 10));
//                        } catch (Exception e) {
//                            System.out.println("Exception occurred during date parsing date: " + fact.getFuneralReservationDate().substring(0, 10));
//                        }
//                        gravesReservationDates.put(fact.getGraveId(), reservationDate);
//                    }
//                    if (!gravesPurchaseDates.containsKey(fact.getGraveId()) && fact.getGravePurchaseDate() != null) {
//                        Date purchaseDate = new Date();
//                        try {
//                        purchaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(fact.getGravePurchaseDate().substring(0, 10));
//                        } catch (Exception e) {
//                            System.out.println("Exception occurred during date parsing date: " + fact.getFuneralReservationDate().substring(0, 10));
//                        }
//                        gravesPurchaseDates.put(fact.getGraveId(), purchaseDate);
//                    }
//                    return null;
//                });

        for (String id: gravesReservationDates.keySet()) {
            if (gravesPurchaseDates.containsKey(id)) {
                LocalDate reservationDate = gravesReservationDates
                        .get(id).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                LocalDate purchaseDate = gravesPurchaseDates
                        .get(id).toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                gravesReservationToPurchaseTime.put(id, ChronoUnit.DAYS.between(reservationDate, purchaseDate));
            }
        }

        Double averageReservationToPurchaseTime = gravesReservationToPurchaseTime
                .values()
                .stream()
                .mapToDouble(Long::doubleValue)
                .average()
                .orElse(0);

        averageReservationToPurchaseTime = new BigDecimal(averageReservationToPurchaseTime).setScale(3, RoundingMode.HALF_UP).doubleValue();

        graveReportDTO.setAverageReservationToPurchaseTime(averageReservationToPurchaseTime);

        // average number of graves per day
        HashMap<String, Long> gravesCountByDays = getGraveCountsPerDateLength(10);

        Double averageGravesPerDay = getAverage(gravesCountByDays);

        graveReportDTO.setAverageGravesPerDay(averageGravesPerDay);

        // median of number of graves per day
        Double medianGravesPerDay = getMedian(gravesCountByDays);

        graveReportDTO.setMedianGravesPerDay(medianGravesPerDay);

        // mode of number of graves per day
        Long mode = getMode(gravesCountByDays);

        graveReportDTO.setModeGravesPerDay(mode);

        // average number of graves per month
        HashMap<String, Long> gravesCountByMonths = getGraveCountsPerDateLength(7);

        Double averageGravesPerMonth = getAverage(gravesCountByMonths);

        graveReportDTO.setAverageGravesPerMonth(averageGravesPerMonth);

        // median of number of graves per month
        Double medianGravesPerMonth = getMedian(gravesCountByMonths);

        graveReportDTO.setMedianGravesPerMonth(medianGravesPerMonth);

        // mode of number of graves per month
        mode = getMode(gravesCountByMonths);

        graveReportDTO.setModeGravesPerMonth(mode);

        // average number of graves per year
        HashMap<String, Long> gravesCountByYears = getGraveCountsPerDateLength(4);

        Double averageGravesPerYear = getAverage(gravesCountByYears);

        graveReportDTO.setAverageGravesPerYear(averageGravesPerYear);

        // median of number of graves per year
        Double medianGravesPerYear = getMedian(gravesCountByYears);

        graveReportDTO.setMedianGravesPerYear(medianGravesPerYear);

        // mode of number of graves per year
        mode = getMode(gravesCountByYears);

        graveReportDTO.setModeGravesPerYear(mode);

        // number of deceased per Grave - HashMap
        HashMap<String, Long> deceasedPerGrave = getDeceasedPerGrave();

        graveReportDTO.setDeceasedPerGrave(new ArrayList<Long>(deceasedPerGrave.values()));

        // average number of deceased per Grave
        Double averageDeceasedPerGrave = getAverage(deceasedPerGrave);

        graveReportDTO.setAverageDeceasedPerGrave(averageDeceasedPerGrave);

        // median of number of deceased per Grave
        Double medianDeceasedPerGrave = getMedian(deceasedPerGrave);

        graveReportDTO.setMedianDeceasedPerGrave(medianDeceasedPerGrave);

        // mode of number of deceased per Grave
        mode = getMode(deceasedPerGrave);

        graveReportDTO.setModeDeceasedPerGrave(mode);

        // number of graves per User - HashMap
        HashMap<String, Long> gravesPerUser = getGravesPerUser();

        graveReportDTO.setGravesPerUser(new ArrayList<Long>(gravesPerUser.values()));

        // average number of graves per User
        Double averageGravesPerUser = getAverage(gravesPerUser);

        graveReportDTO.setAverageGravesPerUser(averageGravesPerUser);

        // median of number of graves per User
        Double medianGravesPerUser = getMedian(gravesPerUser);

        graveReportDTO.setMedianGravesPerUser(medianGravesPerUser);

        // mode of number of graves per User
        Long modeGravesPerUser = getMode(gravesPerUser);

        graveReportDTO.setModeGravesPerUser(modeGravesPerUser);

        return graveReportDTO;
    }
}
