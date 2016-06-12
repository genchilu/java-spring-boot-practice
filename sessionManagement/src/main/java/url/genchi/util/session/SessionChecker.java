package url.genchi.util.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by mac on 2016/6/12.
 */
public abstract class SessionChecker {
    protected SessionChecker next;
    SessionChecker(SessionChecker sessionChecker) {
        this.next = next;
    }
    public abstract Boolean isValidateSession(HttpSession session, HttpServletRequest request);
    Boolean doNext(HttpSession session, HttpServletRequest request) {
        if(next != null) {
            next.isValidateSession(session, request);
        }
        return true;
    }
}
