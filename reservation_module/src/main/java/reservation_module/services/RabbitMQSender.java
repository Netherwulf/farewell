package reservation_module.services;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reservation_module.api.v1.model.FuneralDTO;

@Service
public class RabbitMQSender {
//    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${sample.rabbitmq.exchange}")
    private String exchange;

    @Value("${sample.rabbitmq.routingkey}")
    private String routingkey;

    public RabbitMQSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled
    public void send(FuneralDTO funeralDTO) {
//        String CustomMessage = "This is a message from sender"+ message;

        rabbitTemplate.convertAndSend(exchange, routingkey, funeralDTO);
        System.out.println("Send msg to consumer: " + funeralDTO);
    }
}
