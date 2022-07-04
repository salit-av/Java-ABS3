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

@WebServlet(name="=WithdrawBalance", urlPatterns = {"/withdrawBalance"})
public class WithdrawBalanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        Engine engine = ServletUtils.getEngine(getServletContext());
        String usernameFromParameter = request.getParameter(USERNAME);
        int moneyFromParameter = Integer.parseInt(request.getParameter("money"));

        if (usernameFromParameter == null || usernameFromParameter.equals(USERNAME)) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            int currBalance = engine.printAllCustomers().findCustomer(usernameFromParameter).getBalance();
            if (currBalance >= moneyFromParameter) {
                engine.removeBalanceToCustomer(new DTOBalace(usernameFromParameter, moneyFromParameter));
                response.getOutputStream().print("Balance update successfully");
                response.setStatus(HttpServletResponse.SC_OK);
            }
            else{
                response.getOutputStream().print("Sorry you dont have enough money");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }
}