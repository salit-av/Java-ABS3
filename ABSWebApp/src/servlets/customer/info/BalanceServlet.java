package servlets.customer.info;

import DTO.Customers.DTOCustomer;
import Engine.Engine;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;

import static constants.Constants.USERNAME;

@WebServlet(name="=Balance", urlPatterns = {"/balance"})
public class BalanceServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        Engine engine = ServletUtils.getEngine(getServletContext());
        String usernameFromParameter = request.getParameter(USERNAME);

        if (usernameFromParameter == null || usernameFromParameter.equals(USERNAME)) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            int balance = 0;
            DTOCustomer customer = engine.getDTOCustomer(usernameFromParameter);
            if(customer!= null) {
                balance = customer.getBalance();
            }
            response.getOutputStream().print(balance);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
