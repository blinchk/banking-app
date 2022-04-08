package ee.laus.banking.message;

import ee.laus.banking.model.Account;
import ee.laus.banking.message.structure.AccountMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AccountQueue implements QueueHandler<Account, AccountMessage> {
    private static final String QUEUE_KEY = "accountQueue";

    private final Logger logger = LoggerFactory.getLogger(AccountQueue.class);
    private final RabbitTemplate template;

    @Bean
    public Queue accountMessageQueue() {
        return new Queue(QUEUE_KEY, false);
    }

    @RabbitListener(queues = QUEUE_KEY)
    public void listen(AccountMessage message) {
        logger.info("Received message from {}: {}", QUEUE_KEY, message);
    }

    @Transactional
    public void publish(Account instance) {
        template.convertAndSend(QUEUE_KEY, AccountMessage.of(instance));
    }
}

