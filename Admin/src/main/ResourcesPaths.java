package main;

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

    // global constants
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    public final static String USERNAME = "username";
}
