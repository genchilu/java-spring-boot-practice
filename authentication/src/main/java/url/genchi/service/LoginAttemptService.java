package url.genchi.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by mac on 2016/6/20.
 */
@Service
public class LoginAttemptService {

    @Autowired
    private HttpServletRequest request;
    private final int MAX_ATTEMPT = 2;
    private final int bolckTimeMins = 1;
    private LoadingCache<String, Integer> blockList;

    public LoginAttemptService() {
        blockList = CacheBuilder.newBuilder().
                expireAfterWrite(bolckTimeMins, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });
    }

    public void loginSucceeded(String key) {
        blockList.invalidate(key);
    }

    public void loginFailed(String key) {
        int attempts = 0;
        try {
            attempts = blockList.get(key);
        } catch (ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        blockList.put(key, attempts);
    }

    public boolean isBlocked(String key) {
        try {
            return blockList.get(key) >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }
    }
}