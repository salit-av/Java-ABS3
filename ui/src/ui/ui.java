package ui;

import DTO.Customers.DTOBalace;
import DTO.Customers.DTOprintAllCustomers;
import DTO.Customers.DTOprintCustomer;
import DTO.Customers.DTOtransaction;
import DTO.DTOCategories;
import DTO.DTOactivate;
import DTO.Loan.*;
import Engine.Engine;
import Status.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public interface ui {

    public void run();
    public void runXml();
    public void printLoans(DTOprintAllLoans printAllLoans);
    public void printCustomers(DTOprintAllCustomers printAllCustomers);
    public void printCustomersList(List<DTOtransaction> dtoTransactions);
    public void printCustomersMap(Map<String, DTOLoan> dtoLoans, String as);
    public void loadBalance(DTOprintAllCustomers printAllCustomers);
    public void withdrawalBalance(DTOprintAllCustomers printAllCustomers);
    public void activation(DTOactivate dtoActivate);
    public Map<String, DTOLoan> getAllInvestmentLoansAfterTheirSelection(Map<String, DTOLoan> allInvestmentLoans);
    public Map<String, DTOLoan> getAllInvestmentLoans(DTOprintCustomer customer, List<DTOLoan> dtoAllLoans, DTOCategories dtoAllCategories);
    public List<String> printCategoriesAndReturnAnswer(DTOCategories dtoAllCategories);
    public DTOprintCustomer printAllCustomersAndReturnCustomer(DTOprintAllCustomers dtoAllCustomers);


}
