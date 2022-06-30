import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CustomSessionUtils {

    private static final Map<String, Map<String, String>> sessionsStorage = new ConcurrentHashMap<>();

    private static final String SESSION_ID = "CUSTOM_SESSION_ID";

    public static void setAttribute(HttpServletRequest req, HttpServletResponse resp, String attributeName, String attributeValue) {
        String sessionId = findSessionId(req).orElseGet(() -> UUID.randomUUID().toString());
        sessionsStorage.computeIfPresent(sessionId, (key, value) -> {
            value.replace(attributeName, attributeValue);
            return value;
        });

        sessionsStorage.computeIfAbsent(sessionId, value -> {
            Map<String, String> sessionAttributes = new HashMap<>();
            sessionAttributes.put(attributeName, attributeValue);
            resp.addCookie(new Cookie(SESSION_ID, sessionId));
            return sessionAttributes;
        });
    }

    private static Optional<String> findSessionId(HttpServletRequest req) {
        return findCustomSessionCookie(req.getCookies()).map(Cookie::getValue);
    }

    public static String getAttribute(HttpServletRequest req, String attributeName) {
        return findSessionId(req)
                .map(sessionsStorage::get)
                .map(sessionAttributes -> sessionAttributes.get(attributeName))
                .orElse(null);
    }

    private static Optional<Cookie> findCustomSessionCookie(Cookie[] cookies) {
        if (Objects.isNull(cookies)) {
            return Optional.empty();
        }
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(SESSION_ID))
                .findAny();
    }
}
