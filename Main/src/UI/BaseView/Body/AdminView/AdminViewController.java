package UI.BaseView.Body.AdminView;

import DTO.Customers.DTOCustomer;
import DTO.Loan.*;
import Engine.Engine;
import Engine.Yaz;
import Exceptions.*;
import UI.BaseView.Body.BodyController;
import UI.BaseView.Body.Components.Customers.AdminView.SingleCustomerController;
import UI.BaseView.Body.Components.Loans.AdminView.SingleLoanController;
import UI.BaseView.Header.HeaderController;
import UI.Exceptions.ExceptionsController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static UI.CommonResourcesPaths.*;


public class AdminViewController extends BodyController {
    @FXML private BodyController bodyController;

    @FXML Button increaseYazButton;
    @FXML Button loadFileButton;
    @FXML TreeView<String> loansTV;
    @FXML TreeView<String> customersTV;

    private SimpleBooleanProperty isFileSelected;
    //private SimpleStringProperty selectedFileProperty;

    private Stage primaryStage;
    private Engine engine;

    public AdminViewController() {
       //this.bodyController = bodyController;
        isFileSelected = new SimpleBooleanProperty(false);
        //selectedFileProperty = new SimpleStringProperty();
    }

@FXML
    public void initialize() {
    if (bodyController != null) {
        bodyController.setAdminViewController(this);
    }

    //increaseYazButton.disableProperty().bind(isFileSelected.not());
}

    public void setBodyController(BodyController bodyController) {
        this.bodyController = bodyController;
    }

    @FXML
    public void openFileButtonAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }
        String absolutePath = selectedFile.getAbsolutePath();
        if(loadXmlAndCheckExceptions(absolutePath)) {
            loadFileInfo(absolutePath);
        }

    }

    public boolean loadXmlAndCheckExceptions(String absolutePath) {
        try {
            bodyController.getMainController().getEngine().loadFromXML(absolutePath);
            return true;
        } catch (loanWhoseCustomerIsNotInSystemException | customersWithTheSameNameException | paymentRateIncorrectException | referenceToCategoryThatIsntDefinedException ex) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                URL url = getClass().getResource(EXCEPTIONS_FXML_RESOURCE);
                fxmlLoader.setLocation(url);
                HBox exceptionHB = fxmlLoader.load(url.openStream());
                ExceptionsController exceptionsController = fxmlLoader.getController();
                exceptionsController.setExceptionMessage(ex.getMessage());

                Stage popup = new Stage();
                popup.initModality(Modality.APPLICATION_MODAL);

                Scene popUpScene = new Scene(exceptionHB, 700, 300);
                popup.setScene(popUpScene);
                popup.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public void loadFileInfo(String absolutePath) {
        HeaderController headerController = bodyController.getMainController().getHeaderComponentController();
        headerController.currentFilePathProperty().set(absolutePath);
        headerController.getViewByCombo().getItems().addAll(bodyController.getMainController().getEngine().printAllCustomers().getAllCustomersName());
        isFileSelected.set(true);
        loadLoans();
        loadCustomers();
    }


    public void loadLoans() {
        List<DTOLoan> allLoansToPrint = bodyController.getMainController().getEngine().printAllLoans().getDTOAllLoans();
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
        List<DTOCustomer> allCustomersToPrint = bodyController.getMainController().getEngine().printAllCustomers().getAllCustomersToPrint();
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

    @FXML
    public void increaseYaz(){
       Yaz currYaz = bodyController.getMainController().getEngine().getCurrentYaz();
       engine.promoteYaz();
       bodyController.getMainController().getHeaderComponentController().currentYazTimeProperty().set("Current Yaz: " + currYaz.getCurrentYaz());
       engine.setLoansWithPaymentsInCustomers();
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
