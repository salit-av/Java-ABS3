package servlets.customer.scramble;

import Engine.Engine;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;

import static constants.Constants.USERNAME;

@WebServlet(name="=ScrambleLoadInvestment", urlPatterns = {"/scrambleLoadInvestment"})
public class ScrambleLoadInvestmentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        Engine engine = ServletUtils.getEngine(getServletContext());
        String usernameFromParameter = request.getParameter(USERNAME);
        int investmentFromParameter = Integer.parseInt(request.getParameter("investment"));

        if (usernameFromParameter == null || usernameFromParameter.equals(USERNAME)) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            int balance = engine.printAllCustomers().findCustomer(usernameFromParameter).getBalance();
            if(balance >= investmentFromParameter){
                response.setStatus(HttpServletResponse.SC_OK);
                response.getOutputStream().print("true");
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getOutputStream().print("false");
            }
        }
    }
}
