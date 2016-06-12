package url.genchi.util.session;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Created by mac on 2016/6/12.
 */
public class IpChecker extends SessionChecker {
    private Logger log = Logger.getLogger(IpChecker.class);
    public IpChecker(SessionChecker next) {
        super(next);
    }
    public Boolean isValidateSession(HttpSession session, HttpServletRequest request) {
        if(session.getAttribute("ip")!= null && session.getAttribute("ip").toString().equals(request.getRemoteAddr())) {
            return doNext(session, request);
        } else {
            log.warn("session ip is not equals request ip");
            return false;
        }
    }
}
