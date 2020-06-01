package purchase_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PurchaseModuleApplication {
    @RequestMapping("/")
    public String home() {
        return "Hello Docker World change";
    }

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                SpringApplication.run(PurchaseModuleApplication.class, args);
    }
}