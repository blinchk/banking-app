package ee.laus.banking.infrastructure.account.balance.queue;

import ee.laus.banking.domain.account.Balance;
import ee.laus.banking.infrastructure.QueueHandler;
import ee.laus.banking.presentation.message.BalanceMessage;
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
public class BalanceQueue implements QueueHandler<Balance, BalanceMessage> {
    private static final String QUEUE_KEY = "balanceQueue";

    private final Logger logger = LoggerFactory.getLogger(BalanceQueue.class);
    private final RabbitTemplate template;

    @Bean
    public Queue balanceMessageQueue() {
        return new Queue(QUEUE_KEY, false);
    }

    @RabbitListener(queues = QUEUE_KEY)
    public void listen(BalanceMessage message) {
        logger.info("Received message from {}: {}", QUEUE_KEY, message);
    }

    public void publish(Balance balance) {
        template.convertAndSend(QUEUE_KEY, BalanceMessage.of(balance));
    }
}

