package exercise;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageSender {


    // Класс, который даёт простой доступ к брокеру сообщений RabbitMQ
    // Позволяет отправлять и получать сообщения
    @Autowired
    RabbitTemplate rabbitTemplate;

    // BEGIN
    public void sendMsg(String message) {
        log.info("Sending message to the queue...");
        rabbitTemplate.convertAndSend("exchange", "key", message);
        log.info("Message sent successfully to the queue");
    }
    // END
}
