package servlets.customer;

import Engine.Engine;
import Exceptions.loansWithTheSameNameException;
import Exceptions.paymentRateIncorrectException;
import Exceptions.referenceToCategoryThatIsntDefinedException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.ServletUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static constants.Constants.PATH_XML;
import static constants.Constants.USERNAME;

@WebServlet(name="=LoadFileServlet", urlPatterns = {"/loadFile"})
public class LoadFileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
       // response.setContentType("text/plain;charset=UTF-8");
        response.setContentType("application/json");

        Engine engine = ServletUtils.getEngine(getServletContext());
        String usernameFromParameter = request.getParameter(USERNAME);
        String pathXmlFromParameter = request.getParameter(PATH_XML);

        if(usernameFromParameter==null||pathXmlFromParameter==null) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
        }

        List<String> message = getLoadFromXmlList(pathXmlFromParameter, engine, usernameFromParameter);
        if(message.get(0).equals("false")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getOutputStream().print(message.get(1));
        }
        else {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getOutputStream().print(message.get(1));
        }

    }

    private List<String> getLoadFromXmlList(String path, Engine engine, String userName) {
        List<String> res = new ArrayList<>();
        try {
            engine.loadFromXML(path, userName);
            res.add("true");
            res.add("The file was successfully open");
        } catch (loansWithTheSameNameException | paymentRateIncorrectException | referenceToCategoryThatIsntDefinedException e) {
            res.add("false");
            res.add(e.getMessage());
        }
        return res;
    }
}



