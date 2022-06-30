import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Cleanup;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/evening")
public class EveningServlet extends HttpServlet {

    private static final String DEFAULT_NAME = "Buddy";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = getName(req);

        @Cleanup PrintWriter writer = resp.getWriter();
        writer.printf("Good evening, %s!%n", name);
        writer.flush();
    }

    private static String getName(HttpServletRequest req) {
        Optional.ofNullable(req.getParameter("name"))
                .ifPresent(name -> req.getSession().setAttribute("name", name));
        return (String) Optional.ofNullable(req.getSession().getAttribute("name")).orElse(DEFAULT_NAME);
    }
}
