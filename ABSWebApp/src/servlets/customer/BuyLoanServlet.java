package servlets.customer;

import Engine.Engine;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;

import static constants.Constants.USERNAME;

@WebServlet(name="=BuyLoan", urlPatterns = {"/buyLoan"})
public class BuyLoanServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        Engine engine = ServletUtils.getEngine(getServletContext());
        String usernameFromParameter = request.getParameter(USERNAME);
        String lendersNameFromParameter = request.getParameter("lendersName");
        String idFromParameter = request.getParameter("loanID");

        if (usernameFromParameter == null || usernameFromParameter.equals(USERNAME)) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            boolean res = engine.buyLoan(usernameFromParameter, lendersNameFromParameter, idFromParameter);
            if (res) {
                response.getOutputStream().print("Purchase completed successfully");
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.getOutputStream().print("Sorry you dont have enough money");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }
}
