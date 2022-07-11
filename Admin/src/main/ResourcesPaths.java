package main;

import com.google.gson.Gson;

public class ResourcesPaths {
    // fxml
    public final static String MAIN_APP_FXML = "/Components/Main/mainApp.fxml";
    public final static String ADMIN_LOGIN_FXML = "/Login/adminLogin.fxml";
    public final static String ADMIN_VIEW_FXML = "/AdminView/adminView.fxml";
    public final static String SINGLE_LOAN_ADMIN_VIEW_FXML_RESOURCE = "/Components/Loans/SingleLoanView/singleLoanView.fxml";
    public final static String SINGLE_CUSTOMER_ADMIN_VIEW_FXML_RESOURCE = "/Components/Customers/SingleCustomer.fxml";
    public final static String LENDERS_LIST_FXML_RESOURCE = "/Components/Loans/LendersList/LendersList.fxml";

    // Server resources locations
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/ABSWebApp";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;

    public final static String LOGIN_PAGE = FULL_SERVER_PATH + "/loginAdminServlet";
    public final static String LIST_LOANS_ADMIN = FULL_SERVER_PATH + "/listLoansAdmin";
    public final static String LIST_CUSTOMERS_ADMIN = FULL_SERVER_PATH + "/listCustomersAdmin";
    public final static String CURR_YAZ = FULL_SERVER_PATH + "/currYaz";
    public final static String INCREASE_YAZ = FULL_SERVER_PATH + "/increaseYaz";
    public final static String START_READONLY_MOOD = FULL_SERVER_PATH + "/startReadonlyMood";
    public final static String STOP_READONLY_MOOD = FULL_SERVER_PATH + "/stopReadonlyMood";





    // global constants
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    public final static String USERNAME = "username";
    public final static int REFRESH_RATE = 2000;

    // GSON instance
    public final static Gson GSON_INSTANCE = new Gson();
}
