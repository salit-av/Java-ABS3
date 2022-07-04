package main;

import com.google.gson.Gson;

public class ResourcesPath {
    // fxml locations
    public final static String MAIN_APP_FXML = "/Components/Main/mainApp.fxml";
    public final static String CUSTOMERS_LOGIN_FXML = "/Components/Login/customersLogin.fxml";
    public final static String CUSTOMER_VIEW_FXML = "/Components/CustomerView/customerView.fxml";

    public final static String EXCEPTIONS_FXML_RESOURCE = "/UI/Exceptions/exceptions.fxml";
    public final static String SINGLE_LOAN_CUSTOMERS_VIEW_FXML_RESOURCE = "/Components/Loans/singleLoan.fxml";
    public final static String CHARGE_VIEW_FXML_RESOURCE = "/Components/Transactions/charge.fxml";
    public final static String WITHDRAW_VIEW_FXML_RESOURCE = "/Components/Transactions/withdraw.fxml";
    public final static String PAYME_VIEW_FXML_RESOURCE = "/Components/Transactions/Payments/payMe.fxml";
    public final static String SCRAMBLE_AREA_VIEW_FXML_RESOURCE = "/Components/Notifications/scrambleArea.fxml";
    public final static String SINGLE_TRANSACTION_CUSTOMERS_VIEW_FXML_RESOURCE = "/Components/Transactions/singleTransaction.fxml";
    public final static String POPUP_NOTIFICATION_FXML_RESOURCE = "/Components/Notifications/notificationPopUp.fxml";
    public final static String NOTIFICATION_AREA_FXML_RESOURCE = "/Components/Notifications/notificationArea.fxml";


    // global constants
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");
    public final static String USERNAME = "username";
    public final static int REFRESH_RATE = 2000;
    public final static String CHAT_LINE_FORMATTING = "%tH:%tM:%tS | %.10s: %s%n";

    // Server resources locations
    public final static String BASE_DOMAIN = "localhost";
    private final static String BASE_URL = "http://" + BASE_DOMAIN + ":8080";
    private final static String CONTEXT_PATH = "/ABSWebApp";
    private final static String FULL_SERVER_PATH = BASE_URL + CONTEXT_PATH;

    public final static String LOGIN_PAGE = FULL_SERVER_PATH + "/loginShortResponse";
    public final static String LOAD_FILE = FULL_SERVER_PATH + "/loadFile";
    //info
    public final static String BALANCE = FULL_SERVER_PATH + "/balance";
    public final static String LIST_LOANS_AS_BORROWER = FULL_SERVER_PATH + "/listLoansAsBorrower";
    public final static String LIST_LOANS_AS_LENDER = FULL_SERVER_PATH + "/listLoansAsLender";
    public final static String LIST_TRANSACTIONS = FULL_SERVER_PATH + "/listTransactions";
    public final static String CHARGE_BALANCE = FULL_SERVER_PATH + "/chargeBalance";

    public final static String ADD_LOAN = FULL_SERVER_PATH + "/addLoan";

    public final static String LOGOUT = FULL_SERVER_PATH + "/chat/logout";
    public final static String SEND_CHAT_LINE = FULL_SERVER_PATH + "/pages/chatroom/sendChat";
    public final static String CHAT_LINES_LIST = FULL_SERVER_PATH + "/chat";

    // GSON instance
    public final static Gson GSON_INSTANCE = new Gson();
}
