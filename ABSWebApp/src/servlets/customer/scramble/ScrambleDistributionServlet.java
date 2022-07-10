package servlets.customer.scramble;

import DTO.Customers.DTOCustomer;
import Engine.Engine;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static constants.Constants.USERNAME;

@WebServlet(name="=ScrambleDistribution", urlPatterns = {"/scrambleDistribution"})
public class ScrambleDistributionServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        Gson gson = new Gson();

        Engine engine = ServletUtils.getEngine(getServletContext());
        String usernameFromParameter = request.getParameter(USERNAME);
        int investmentFromParameter = Integer.parseInt(request.getParameter("investment"));
        String loansChosenFromParameter = request.getParameter("loansChosen");
        String[] loansArr= gson.fromJson(loansChosenFromParameter, String[].class);
        List<String> loansChosen = Arrays.stream(loansArr).collect(Collectors.toList());

        if (usernameFromParameter == null || usernameFromParameter.equals(USERNAME)) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            if(loansChosen.isEmpty()){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getOutputStream().print("false");
            }
            else {
                DTOCustomer customer = engine.getDTOCustomer(usernameFromParameter);
                engine.distributionOfMoneyForLoans(customer, investmentFromParameter, loansChosen);
                response.getOutputStream().print("true");
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }
}
