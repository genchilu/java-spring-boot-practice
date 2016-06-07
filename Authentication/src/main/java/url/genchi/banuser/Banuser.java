package url.genchi.banuser;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mac on 2016/6/6.
 */
public class Banuser {
    static class BanInfo {
        BanInfo(int failecount, boolean inAuthenticating) {
            this.failecount = failecount;
            this.inAuthenticating = inAuthenticating;
        }
        int failecount;
        boolean inAuthenticating;
        long latestTryTime;
    }
    private static final int maxFailedCount = 5;
    private static final int forgetFailedMin = 1;
    private static ConcurrentHashMap<String, BanInfo> blocklist = new ConcurrentHashMap<String, BanInfo>();
    public static boolean isInAuthentication(String ip) {
        if(blocklist.containsKey(ip)) {
            if(blocklist.get(ip).inAuthenticating) {
                return true;
            }
            return false;
        } else {
            blocklist.put(ip, new BanInfo(0, true));
            return false;
        }
    }

    public static boolean isAllow(String ip) {
        if(blocklist.get(ip).failecount < maxFailedCount) {
            return true;
        } else if((System.currentTimeMillis() - blocklist.get(ip).latestTryTime) > forgetFailedMin * 60 * 1000) {
            blocklist.get(ip).failecount = 0;
            return true;
        }
        return false;
    }
    public static void successfulAttempt(String ip) {
        blocklist.remove(ip);
    }
    public static void failedAttempt(String ip){
        blocklist.get(ip).inAuthenticating=false;
        blocklist.get(ip).failecount = blocklist.get(ip).failecount+1;
        blocklist.get(ip).latestTryTime = System.currentTimeMillis();
    }
}
