package url.genchi;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class AsyncService {
    //@Async
    public Future<String> findUser(String user) throws InterruptedException {
        System.out.println("Looking up " + user);
        Thread.sleep(10000);
        return new AsyncResult<String>(user);
    }
}
