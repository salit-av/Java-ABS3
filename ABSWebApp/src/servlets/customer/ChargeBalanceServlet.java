package servlets.customer;

import DTO.Customers.DTOBalace;
import Engine.Engine;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;

import static constants.Constants.USERNAME;

@WebServlet(name="=ChargeBalance", urlPatterns = {"/chargeBalance"})
public class ChargeBalanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        Engine engine = ServletUtils.getEngine(getServletContext());
        String usernameFromParameter = request.getParameter(USERNAME);
        int moneyFromParameter = Integer.parseInt(request.getParameter("money"));

        if (usernameFromParameter == null || usernameFromParameter.equals(USERNAME)) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            engine.addBalanceToCustomer(new DTOBalace(usernameFromParameter, moneyFromParameter));
            response.getOutputStream().print("Balance update successfully");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
