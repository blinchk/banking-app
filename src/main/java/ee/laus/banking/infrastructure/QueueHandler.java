package ee.laus.banking.infrastructure;

import java.io.Serializable;

public interface QueueHandler<I, O extends Serializable> {
    void listen(O message);

    void publish(I instance);
}
