package ee.laus.banking.infrastructure.account.customer.queue;

import ee.laus.banking.domain.account.Customer;
import ee.laus.banking.infrastructure.QueueHandler;
import ee.laus.banking.infrastructure.account.balance.queue.BalanceQueue;
import ee.laus.banking.presentation.message.CustomerMessage;
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
public class CustomerQueue implements QueueHandler<Customer, CustomerMessage> {
    private static final String QUEUE_KEY = "customerQueue";

    private final Logger logger = LoggerFactory.getLogger(BalanceQueue.class);
    private final RabbitTemplate template;

    @Bean
    public Queue customerMessageQueue() {
        return new Queue(QUEUE_KEY, false);
    }

    @RabbitListener(queues = QUEUE_KEY)
    public void listen(CustomerMessage message) {
        logger.info("Received message from {}: {}", QUEUE_KEY, message);
    }

    public void publish(Customer instance) {
        template.convertAndSend(QUEUE_KEY, CustomerMessage.of(instance));
    }
}
