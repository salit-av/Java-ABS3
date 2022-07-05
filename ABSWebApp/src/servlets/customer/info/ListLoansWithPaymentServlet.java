package servlets.customer.info;

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

@WebServlet(name="=ListLoansWithPayment", urlPatterns = {"/listLoansWithPayment"})
public class ListLoansWithPaymentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        Engine engine = ServletUtils.getEngine(getServletContext());
        String usernameFromParameter = request.getParameter(USERNAME);

        if (usernameFromParameter == null || usernameFromParameter.equals(USERNAME)) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {

            DTOLoan[] loansWithPayment = engine.getDTOCustomer(usernameFromParameter).getDTOloansWithPayments().toArray(new DTOLoan[0]);
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                String json = gson.toJson(loansWithPayment);
                out.println(json);
                out.flush();
            }
        }
    }
}
