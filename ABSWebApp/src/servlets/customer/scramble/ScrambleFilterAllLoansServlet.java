package servlets.customer.scramble;

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
import java.util.Arrays;

import static constants.Constants.USERNAME;

@WebServlet(name="=ScrambleFilterAllLoans", urlPatterns = {"/scrambleFilterAllLoans"})
public class ScrambleFilterAllLoansServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        response.setContentType("application/json");

        Engine engine = ServletUtils.getEngine(getServletContext());
        String usernameFromParameter = request.getParameter(USERNAME);
        int investmentFromParameter = Integer.parseInt(request.getParameter("investment"));
        String jsonArrayOfListCategoriesFromParameter = request.getParameter("categoriesAfterFilter");
        String[] categoriesAfterFilter = gson.fromJson(jsonArrayOfListCategoriesFromParameter, String[].class);
        int minInterestFromParameter = Integer.parseInt(request.getParameter("minInterest"));
        int minYazFromParameter = Integer.parseInt(request.getParameter("minYaz"));
        int maxLoansOpenFromParameter = Integer.parseInt(request.getParameter("maxLoansOpen"));
        int maxOwnershipFromParameter = Integer.parseInt(request.getParameter("maxOwnership"));

        if (usernameFromParameter == null || usernameFromParameter.equals(USERNAME)) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        } else {
            DTOLoan[] loans = engine.filterAllInvestmentLoans(investmentFromParameter, Arrays.asList(categoriesAfterFilter), minInterestFromParameter, minYazFromParameter, maxLoansOpenFromParameter, usernameFromParameter).
                    getDTOAllLoans().toArray(new DTOLoan[0]);
            try (PrintWriter out = response.getWriter()) {
                String json = gson.toJson(loans);
                out.println(json);
                out.flush();
            }
        }
    }
}
