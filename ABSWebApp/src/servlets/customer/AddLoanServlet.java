package servlets.customer;

import Engine.Engine;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;

import static constants.Constants.USERNAME;

@WebServlet(name="=AddLoan", urlPatterns = {"/addLoan"})
public class AddLoanServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        Engine engine = ServletUtils.getEngine(getServletContext());
        String usernameFromParameter = request.getParameter(USERNAME);
        String idFromParameter = request.getParameter("id");
        String categoryFromParameter = request.getParameter("category");
        String capitalFromParameter = request.getParameter("capital");
        String totalYazTimeFromParameter = request.getParameter("totalYazTime");
        String paysEveryYazFromParameter = request.getParameter("paysEveryYaz");
        String internistPerPaymentFromParameter = request.getParameter("internistPerPayment");

        if (usernameFromParameter == null || usernameFromParameter.equals(USERNAME)) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            String message = engine.addLoan(usernameFromParameter, idFromParameter, categoryFromParameter, Integer.parseInt(capitalFromParameter), Integer.parseInt(totalYazTimeFromParameter), Integer.parseInt(paysEveryYazFromParameter), Integer.parseInt(internistPerPaymentFromParameter));
            response.getOutputStream().print(message);
            if (message.equals("Loan added successfully")) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }
}
