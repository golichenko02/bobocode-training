import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Cleanup;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/evening/custom")
public class EveningServletWithCustomSession extends HttpServlet {
    private static final String DEFAULT_NAME = "Buddy";
    private static final String QUERY_PARAM = "name";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = getName(req, resp);

        @Cleanup PrintWriter writer = resp.getWriter();
        writer.printf("Good evening, %s!%n", name);
        writer.flush();
    }

    private static String getName(HttpServletRequest req, HttpServletResponse resp) {
        Optional<String> reqParameter = Optional.ofNullable(req.getParameter(QUERY_PARAM));

        String name = reqParameter.map(value -> CustomSessionUtils.getAttribute(req, QUERY_PARAM))
                .orElse(DEFAULT_NAME);

        reqParameter.ifPresent(value -> CustomSessionUtils.setAttribute(req, resp, QUERY_PARAM, value));
        return name;
    }
}
