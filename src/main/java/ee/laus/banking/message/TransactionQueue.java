package ee.laus.banking.message;

import ee.laus.banking.model.Transaction;
import ee.laus.banking.message.structure.TransactionMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionQueue implements QueueHandler<Transaction, TransactionMessage> {
    private static final String QUEUE_KEY = "transactionQueue";

    private final Logger logger = LoggerFactory.getLogger(TransactionQueue.class);
    private final RabbitTemplate template;

    @Bean
    public Queue transactionMessageQueue() {
        return new Queue(QUEUE_KEY, false);
    }

    @RabbitListener(queues = QUEUE_KEY)
    public void listen(TransactionMessage message) {
        logger.info("Received message from {}: {}", QUEUE_KEY, message);
    }

    public void publish(Transaction instance) {
        template.convertAndSend(QUEUE_KEY, TransactionMessage.of(instance));
    }
}

