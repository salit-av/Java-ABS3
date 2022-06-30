package AdminView;

import Components.Customers.SingleCustomerController;
import Components.Loans.SingleLoanView.SingleLoanController;
import DTO.Customers.DTOCustomer;
import DTO.Loan.DTOLoan;
import Engine.Engine;
import jakarta.servlet.http.HttpServlet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import utils.ServletUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static main.ResourcesPaths.SINGLE_CUSTOMER_ADMIN_VIEW_FXML_RESOURCE;
import static main.ResourcesPaths.SINGLE_LOAN_ADMIN_VIEW_FXML_RESOURCE;


public class AdminViewController extends HttpServlet {
    @FXML Button increaseYazButton;
    @FXML TreeView<String> loansTV;
    @FXML TreeView<String> customersTV;

    private String username;
    private boolean isAdminLogin;

    public AdminViewController() {
        loadAdmin();
        isAdminLogin = false;
    }

@FXML
    public void initialize() {
}

    public Engine getEngine() {
        return ServletUtils.getEngine(getServletContext());
    }

    public void loadAdmin(){
        loadLoans();
        loadCustomers();
    }

    public void loadLoans() {
        List<DTOLoan> allLoansToPrint = getEngine().printAllLoans().getDTOAllLoans();
        TreeItem<String> treeLoans = new TreeItem<>("There is not list of Loans");
        if (!allLoansToPrint.isEmpty()) {
            treeLoans.setValue("List of Loans");

            for (DTOLoan loan : allLoansToPrint) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    URL url = getClass().getResource(SINGLE_LOAN_ADMIN_VIEW_FXML_RESOURCE);
                    fxmlLoader.setLocation(url);
                    VBox singleLoan = fxmlLoader.load(url.openStream());

                    SingleLoanController singleLoanController = fxmlLoader.getController();
                    singleLoanController.setInfoOfLoan(loan);

                    treeLoans.getChildren().add(singleLoanController.getRoot());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        loansTV.setRoot(treeLoans);
    }

    public void loadCustomers() {
        List<DTOCustomer> allCustomersToPrint = getEngine().printAllCustomers().getAllCustomersToPrint();
        TreeItem<String> treeCustomers = new TreeItem<>("There is not list of Customers");
        if (!allCustomersToPrint.isEmpty()) {
            treeCustomers.setValue("List of Customers");
            for(DTOCustomer cus: allCustomersToPrint) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    URL url = getClass().getResource(SINGLE_CUSTOMER_ADMIN_VIEW_FXML_RESOURCE);
                    fxmlLoader.setLocation(url);
                    VBox singleCus = fxmlLoader.load(url.openStream());

                    SingleCustomerController singleCustomerController = fxmlLoader.getController();
                    singleCustomerController.setInfoOfCustomer(cus);

                    treeCustomers.getChildren().add(singleCustomerController.getRoot());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        customersTV.setRoot(treeCustomers);

    }
/*
    @FXML
    public void increaseYaz(){
       Yaz currYaz = getEngine().getCurrentYaz();
       engine.promoteYaz();
       bodyController.getMainController().getHeaderComponentController().currentYazTimeProperty().set("Current Yaz: " + currYaz.getCurrentYaz());
       engine.setLoansWithPaymentsInCustomers();
    }*/

}
