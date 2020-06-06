package analytical_module.controllers;

import analytical_module.api.v1.model.FactDTO;
import analytical_module.api.v1.model.FactListDTO;
import analytical_module.api.v1.model.GraveReportDTO;
import analytical_module.services.FactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.apache.commons.io.FileUtils;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class FactController {

    private final FactService factService;

    public FactController(FactService factService) {
        this.factService = factService;
    }

    @GetMapping("/facts")
    public FactListDTO getAllFacts() {
        return factService.getAllFacts();
    }

    @GetMapping("/funeralDirectors/{funeralDirectorId}/facts")
    public ResponseEntity getFactsByFuneralDirectorId(@PathVariable Long funeralDirectorId) {
        return factService.getFactsByFuneralDirectorId(funeralDirectorId);
    }

    @GetMapping("/facts/{id}")
    public ResponseEntity getFactById(@PathVariable Long id) {
        return factService.getFactById(id);
    }

    @PostMapping("/facts")
    public FactDTO createNewFact(@RequestBody FactDTO factDTO) {
        return factService.createNewFact(factDTO);
    }

    @GetMapping(value="/funeralReport")
    public ResponseEntity getFuneralReport() {
        return factService.getFuneralReport();
    }

    @GetMapping(value="/graveReport", produces=MediaType.APPLICATION_XML_VALUE)
    public String getGraveReport() {
        GraveReportDTO returnDTO =  factService.getGraveReport();
        XmlMapper xmlMapper = new XmlMapper();
//        xmlMapper.registerModule(new JaxbAnnotationModule());
        String xmlString = "";
        try {
        xmlString = xmlMapper.writeValueAsString(returnDTO);
        } catch (JsonProcessingException e) {
            // handle exception
        }

//        File xsltFile = new File(Thread.currentThread().getContextClassLoader().getResource("xslt/grave-report.xslt").getFile());
        ClassLoader classLoader = FactController.class.getClassLoader();
        InputStream is = classLoader.getResourceAsStream("xslt/grave-report.xslt");
        String xsltContent = "";
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            xsltContent = (String)reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } else {
            throw new RuntimeException("resource not found");
        }
//        try {
//            FileInputStream fisTargetFile = new FileInputStream(xsltFile);
//            xsltContent = FileUtils.readFileToString(xsltFile, "UTF-8");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        StringReader xmlReader = new StringReader(xmlString);
        StreamSource xmlSource = new StreamSource(xmlReader);

        StringReader xslReader = new StringReader(xsltContent);
        StreamSource xslSource = new StreamSource(xslReader);

        TransformerFactory factory  = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = factory.newTransformer(xslSource);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }

        StringWriter resultWriter = new StringWriter();
        try {
            transformer.transform(xmlSource, new StreamResult(resultWriter));
        } catch (TransformerException e) {
            e.printStackTrace();
        }

//        System.out.println(resultWriter.toString());

//        return xmlString;
        return resultWriter.toString();

    }
}
