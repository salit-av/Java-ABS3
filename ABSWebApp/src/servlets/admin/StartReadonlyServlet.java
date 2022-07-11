package servlets.admin;

import Engine.Engine;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;

import static constants.Constants.USERNAME;

@WebServlet(name="=StartReadonlyMood", urlPatterns = {"/startReadonlyMood"})
public class StartReadonlyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        Engine engine = ServletUtils.getEngine(getServletContext());
        String usernameFromParameter = request.getParameter(USERNAME);
        String yazFromParameter = request.getParameter("yaz");

        if (usernameFromParameter == null || usernameFromParameter.equals(USERNAME)) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            engine.startReadonly(Integer.parseInt(yazFromParameter));
            response.getOutputStream().print(engine.isReadonly());
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
