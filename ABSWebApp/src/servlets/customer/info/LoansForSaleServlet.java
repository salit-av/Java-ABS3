package servlets.customer.info;

import DTO.Customers.DTOCustomer;
import DTO.Loan.DTOLoan;
import Engine.Engine;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;
import java.io.PrintWriter;

import static constants.Constants.USERNAME;

@WebServlet(name="=LoansForSale", urlPatterns = {"/loansForSale"})
public class LoansForSaleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        Engine engine = ServletUtils.getEngine(getServletContext());
        String usernameFromParameter = request.getParameter(USERNAME);

        if (usernameFromParameter == null || usernameFromParameter.equals(USERNAME)) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            DTOCustomer customer = engine.getDTOCustomer(usernameFromParameter);
            DTOLoan[] loansForSale = new DTOLoan[0];
            if(customer!= null) {
                loansForSale = customer.getDTOloansForSale().toArray(new DTOLoan[0]);
            }
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                String json = gson.toJson(loansForSale);
                out.println(json);
                out.flush();
            }
        }
    }
}
