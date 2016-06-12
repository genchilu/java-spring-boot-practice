package url.genchi.util.session;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by mac on 2016/6/12.
 */
public class AgentChecker extends SessionChecker {
    private Logger log = Logger.getLogger(AgentChecker.class);
    public AgentChecker(SessionChecker next) {
        super(next);
    }
    public Boolean isValidateSession(HttpSession session, HttpServletRequest request) {
        if(session.getAttribute("agent") != null &&
                session.getAttribute("session").toString().equals(request.getHeader("User-Agent"))) {
            return doNext(session, request);
        } else {
            log.warn("session agent is not equals request agent");
            return false;
        }
    }
}
