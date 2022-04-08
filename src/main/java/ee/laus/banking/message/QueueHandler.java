package ee.laus.banking.message;

import java.io.Serializable;

public interface QueueHandler<I, O extends Serializable> {
    void listen(O message);

    void publish(I instance);
}
