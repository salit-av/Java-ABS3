package ui;

import DTO.Customers.DTOBalace;
import DTO.Customers.DTOprintAllCustomers;
import DTO.Customers.DTOprintCustomer;
import DTO.Customers.DTOtransaction;
import DTO.DTOCategories;
import DTO.DTOactivate;
import DTO.Loan.*;
import Engine.Engine;
import Status.*;

import java.util.*;

public class uiConsole implements ui {
    private Engine engine;
    private Menus menus;
    private Scanner scanner = new Scanner(System.in);
    boolean isFileRead;


    public uiConsole() {
        this.engine = new Engine();
        this.menus = new Menus();
    }

    public void run() {
        int choice = menus.printMenuAndReturnChoice();
        while (choice != 8) {
            if (choice == 1) {
                runXml();
            }
            else if (choice == 2 && isFileRead) {
                printLoans(engine.printAllLoans());
            }
            else if (choice == 3 && isFileRead) {
                printCustomers(engine.printAllCustomers());
            }
            else if(choice == 4 && isFileRead){
                loadBalance(engine.printAllCustomers());
            }
            else if(choice == 5 && isFileRead){
                withdrawalBalance(engine.printAllCustomers());
            }
            else if(choice == 6 && isFileRead){
                activation(engine.activate());
            }
            else if(choice == 7 && isFileRead){
                promotingYazAndProvidingPayments(engine.printAllCustomers());
            }
            else if (!isFileRead && choice != 0) {
                menus.printNoFileUploadedToSystem();
            }
            else{
                System.out.println("--------------------ERROR!-------------------");
                System.out.println("   Sorry wrong number, please try again");
                System.out.println("---------------------------------------------");
            }

            choice = menus.printMenuAndReturnChoice();
        }
        menus.printGoodbye();
    }

    public void runXml(){
        System.out.println("Please enter file path:");
        String filePath = scanner.nextLine();

        try{
            engine.loadFromXML(filePath);
            System.out.println("XML file read successfully");
            System.out.println();
            isFileRead = true;
        }
        catch (Exception e){
            System.out.println("--------------------ERROR!-------------------");
            System.out.println(e.getMessage());
            System.out.println("---------------------------------------------");
            System.out.println();
        }
    }

    public void printLoans(DTOprintAllLoans printAllLoans) {
        if(printAllLoans == null || printAllLoans.isEmpty()){
            System.out.println("--------------------------------------------");
            System.out.println("        There is no list of loans!");
            System.out.println("--------------------------------------------");
        }
        else {
            int i = 1;
            System.out.println("---------------------------------------");
            System.out.println("   List of all loans in the system");
            System.out.println("---------------------------------------");
            for (DTOprintLoan loan : printAllLoans.getAllLoansToPrint()) {
                System.out.println("-" + i + "-");
                i++;
                System.out.println("ID: " + loan.getId());
                System.out.println("Owner: " + loan.getOwner());
                System.out.println("Category: " + loan.getCategory());
                System.out.println("Capital at start: " + loan.getCapitalAtStart());
                System.out.println("Interest per payment: " + loan.getInterestPerPayment());
                System.out.println("Interval of payment: " + loan.getPaysEveryYaz());
                System.out.println("Status: " +  loan.getStatus());
                System.out.println("");
                printStatusLoan(loan);
                System.out.println("");

            }
        }
    }

    public void printStatusLoan(DTOprintLoan loan) {
        Status status = loan.getStatus();
        Map<String, DTOpayments> lenders = loan.getLenders();
        if (lenders == null || lenders.isEmpty()) {
            System.out.println("--------------------------------");
            System.out.println("There is no list of all lenders!");
            System.out.println("--------------------------------");

        }
        else {
            System.out.println("---------------------------------------");
            System.out.println("           List of lenders: ");
            System.out.println("---------------------------------------");

            for (String name : lenders.keySet()) {
                // pending

                DTOpayments paymentsOfCus = lenders.get(name);
                System.out.println("***************************************");
                System.out.println("Customer's name: " + name);
                System.out.println("Privet invesment: " + paymentsOfCus.getInvestment());

                if (!status.equals(Status.PENDING)) {
                    List<DTOpayment> allPayments = paymentsOfCus.getAllPayments();

                    if (allPayments == null || allPayments.isEmpty()) {
                        System.out.println("There is no list of payments!");
                    } else {
                        System.out.println("***************************************");
                        System.out.println("       List of all payments:");
                        System.out.println("***************************************");

                        for (DTOpayment payment : allPayments) {
                            // active
                            System.out.println("Yaz of payment: " + payment.getYazPayment());
                            System.out.println("Interest per payment: " + payment.getInterestPerPayment());
                            System.out.println("Capital per payment: " + payment.getCapitalPerPayment());
                            System.out.println("Total payment (interest + capital): " + payment.getTotalPayment());


                            if (status.equals(Status.RISK) && payment.getTotalPayment() == 0) {
                                System.out.println("-Unpaid Payment!!!-");
                            }
                            System.out.println();
                        }
                        // active
                        if (status.equals(Status.ACTIVE) || status.equals(Status.RISK)) {
                            System.out.println("Capital until now: " + paymentsOfCus.getCapitalUntilNow());
                            System.out.println("Interest until now: " + paymentsOfCus.getInterestUntilNow());
                            System.out.println("Capital left to pay: " + (paymentsOfCus.getCapital() - paymentsOfCus.getCapitalUntilNow()));
                            System.out.println("Interest left to pay: " + (paymentsOfCus.getInterest() - paymentsOfCus.getInterestUntilNow()));
                            System.out.println();
                            System.out.println("***************************************");

                        }
                    }
                }
            }

            // pending
            System.out.println("The total amount raised by the lenders: " + (loan.getCapitalAtStart() - loan.getPayLeftFromPendingToActive()));
            System.out.println("The total amount left from Pending to Active: " + loan.getPayLeftFromPendingToActive());

            // active
            if (status.equals(Status.ACTIVE) || status.equals(Status.RISK)) {
                System.out.println("Yaz in which the loan became from Pending to Active: " + loan.getYazFromPendingToActive());
                System.out.println("Next yaz for payment: " + loan.getNextYazToPay());

            }
            // risk
            if (status.equals(Status.RISK)) {
                System.out.println("Count of unpaid payments: " + loan.getCountUnpaidPayments());
                System.out.println("Total money of unpaid payments: " + loan.getTotalUnpaidPayments());
            }

            // finished
            if (status.equals(Status.FINISHED)) {
                System.out.println("Yaz at start of the loan: " + loan.getYazAtFirst());
                System.out.println("Yaz at the end of the loan: " + loan.getYazAtTheEnd());
            }
        }
    }

    public void printCustomers(DTOprintAllCustomers printAllCustomers){
        if(printAllCustomers == null || printAllCustomers.isEmpty()){
            System.out.println("------------------------------");
            System.out.println("There is no list of customers!");
            System.out.println("------------------------------");

        }
        else {
            int i = 1;
            System.out.println("***************************************");
            System.out.println("  List of all customers in the system");
            System.out.println("***************************************");

            for (DTOprintCustomer customer : printAllCustomers.getAllCustomersToPrint()) {
                System.out.println("-" + i + "-");
                i++;
                System.out.println("Customer's name: " + customer.getName());
                printCustomersList(customer.getDTOtransactions());
                printCustomersMap(customer.getDTOloansAsBorrower(), "Borrower");
                printCustomersMap(customer.getDTOloansAsLender(), "Lender");
                System.out.println("");
            }
        }
        System.out.println("");
    }

    public void printCustomersList(List<DTOtransaction> dtoTransactions) {
        if (dtoTransactions == null || dtoTransactions.isEmpty()) {
            System.out.println("---------------------------------");
            System.out.println("There is no list of transactions!");
            System.out.println("---------------------------------");

        }
        else {
            System.out.println("*************************");
            System.out.println("List of all transactions");
            System.out.println("*************************");
            int i = 1;
            for (DTOtransaction transaction : dtoTransactions) {
                System.out.println("Information at transaction number " + i + ":");
                System.out.println("Yaz at execution: " + transaction.getYaz());
                System.out.println("Sum: " + transaction.getPay());
                System.out.println("Income or Expense: " + transaction.getInOrOut());
                System.out.println("Balance before operation: " + transaction.getBeforeTran());
                System.out.println("Balance after operation: " + transaction.getAfterTran());
                System.out.println("");
                i++;
            }
        }
    }

    public void printCustomersMap(Map<String, DTOLoan> dtoLoans, String as) {
        Status status;
        if (dtoLoans == null || dtoLoans.isEmpty()) {
            System.out.println("-----------------------------------------");
            System.out.println("There is no list of loans as " + as + "!");
            System.out.println("-----------------------------------------");

        } else {
            int i = 1;
            System.out.println("*******************************");
            System.out.println("List of loans as " + as);
            System.out.println("*******************************");

            for (String id : dtoLoans.keySet()) {
                System.out.println("-" + i + "-");
                i++;
                DTOLoan loan = dtoLoans.get(id);
                System.out.println("Name: " + loan.getId());
                System.out.println("Category: " + loan.getCategory());
                System.out.println("Capital: " + loan.getCapitalAtStart());
                System.out.println("Pays every: " + loan.getPaysEveryYaz() + " Yaz");
                System.out.println("Interest per payment: " + loan.getInterestPerPayment());
                System.out.println("Sum all loan (capital + interest): " + loan.getAllPayment());

                status = loan.getStatus();
                System.out.println("Status: " + status);

                if (status.equals(Status.PENDING)) {
                    System.out.println("Amount missing to make the loan from pending to active: " + loan.getPayLeftFromPendingToActive());
                } else if (status.equals(Status.ACTIVE)) {
                    System.out.println("Next Yaz for payment: " + loan.getNextYazForPayment());
                    System.out.println("Next payment: " + loan.getNextPayIncludesInterestAndCapital());
                } else if (status.equals(Status.RISK)) {
                    System.out.println("Number of unpaid payments " + loan.getCountUnpaidPayments());
                    System.out.println("In total of: " + loan.getTotalUnpaidPayments());
                } else if (status.equals(Status.FINISHED)) {
                    System.out.println("Yaz at first: " + loan.getYazAtFirst());
                    System.out.println("Yaz at the end: " + loan.getYazAtTheEnd());
                }
                System.out.println("");

            }
        }
    }

    public void loadBalance(DTOprintAllCustomers printAllCustomers) {
        DTOprintCustomer dtoCustomer = printAllCustomersAndReturnCustomer(printAllCustomers);
        String name = dtoCustomer.getName();
        if (dtoCustomer != null) {
            System.out.println("How much to put in his account?");
            int addBalance = menus.giveInt();
            engine.addBalanceToCustomer(new DTOBalace(name, addBalance));
            System.out.println("********************************************");
            System.out.println("Your request has been accepted and executed!");
            System.out.println("Customer had: " + dtoCustomer.getBalance() + ", and now he has: " + (dtoCustomer.getBalance() + addBalance));
            System.out.println("********************************************");

            System.out.println("");
        }
    }

    public void withdrawalBalance(DTOprintAllCustomers printAllCustomers) {
        DTOprintCustomer dtoCustomer = printAllCustomersAndReturnCustomer(printAllCustomers);
        String name = dtoCustomer.getName();
        if(dtoCustomer != null) {
            System.out.println("How much to take out of his account?");
            int removeBalance = menus.giveInt();
            if (removeBalance >= 0 && removeBalance < dtoCustomer.getBalance()) {
                engine.removeBalanceToCustomer(new DTOBalace(name, removeBalance));
                System.out.println("********************************************");
                System.out.println("Your request has been accepted and executed!");
                System.out.println("Customer had: " + dtoCustomer.getBalance() + ", and now he has: " + (dtoCustomer.getBalance() - removeBalance));
                System.out.println("********************************************");
                System.out.println("");
            } else {
                System.out.println("-----------------------------------------------------------------");
                System.out.println("You're confused... you do not have that much money in the account");
                System.out.println("-----------------------------------------------------------------");
            }
        }
    }

    public void activation(DTOactivate dtoActivate) {
        DTOprintCustomer dtoCustomer = printAllCustomersAndReturnCustomer(dtoActivate.getDtoAllCustomers());
        int investmentBalance = menus.printInvestmentBalance(dtoCustomer.getBalance());

        if(investmentBalance != 0) {
            Map<String, DTOLoan> allInvestmentLoans = getAllInvestmentLoans(dtoCustomer, dtoActivate.getDtoAllLoans(), dtoActivate.getDtoAllCategories());
            if (!allInvestmentLoans.isEmpty()) {
                printCustomersMap(allInvestmentLoans, "potential lender");
                allInvestmentLoans = getAllInvestmentLoansAfterTheirSelection(allInvestmentLoans);
                engine.distributionOfMoneyForLoans(dtoCustomer, investmentBalance, allInvestmentLoans);
                System.out.println("********************************************");
                System.out.println("Your request has been accepted and executed!");
                System.out.println("********************************************");
            }
            else {
                System.out.println("-------------------------------------------------------------------");
                System.out.println("You have selected loan parameters so that there is no suitable loan!");
                System.out.println("-------------------------------------------------------------------");
            }
        }
    }

    public Map<String, DTOLoan> getAllInvestmentLoansAfterTheirSelection(Map<String, DTOLoan> allInvestmentLoans) {
        List<Integer> numsOfLoans = new ArrayList<>();
        System.out.println("Please enter the loan numbers you would like to lend to");
        System.out.println("(enter SPACE between numbers AND 0 to end or cancel)");
        int numOfLoan = menus.giveInt();
        while(numOfLoan != 0){
            numsOfLoans.add(numOfLoan);
            numOfLoan = menus.giveInt();
        }
        return engine.getAllInvestmentLoansAfterTheirSelection(allInvestmentLoans, numsOfLoans);
    }

    public Map<String, DTOLoan> getAllInvestmentLoans(DTOprintCustomer customer, List<DTOLoan> dtoAllLoans, DTOCategories dtoAllCategories) {

        List<String> filterCategories = printCategoriesAndReturnAnswer(dtoAllCategories);
        int minimumInterest = menus.printMinimumInterestAndReturnAnswer();
        int minimumYazForAllLoan = menus.printMinimumYazForAllLoanAndReturnAnswer();

         return engine.filterAllInvestmentLoans(dtoAllLoans, filterCategories, minimumInterest, minimumYazForAllLoan, 0, customer);
    }

    public List<String> printCategoriesAndReturnAnswer(DTOCategories dtoAllCategories) {
        List<String> filterCategories = new ArrayList<>();
        if (dtoAllCategories == null || dtoAllCategories.isEmpty()) {
            System.out.println("-------------------------------");
            System.out.println("There is no list of categories!");
            System.out.println("-------------------------------");

        } else {
            int i = 1;
            System.out.println("***************************************************");
            System.out.println("Please select a categories from the following list:");
            System.out.println("***************************************************");

            for (String category : dtoAllCategories.getDtoAllCategories()) {
                System.out.println(i + ". " + category);
                i++;
            }
            System.out.println("(enter its numbers with SPACE between AND 0 to end or cancel)");
            i = menus.giveInt();
            while (i != 0) {
                filterCategories.add(dtoAllCategories.getDtoAllCategories().get(i - 1));
                i = menus.giveInt();
            }
        }
        return filterCategories;
    }

    public DTOprintCustomer printAllCustomersAndReturnCustomer(DTOprintAllCustomers dtoAllCustomers) {
        if (dtoAllCustomers == null || dtoAllCustomers.isEmpty()) {
            System.out.println("------------------------------");
            System.out.println("There is no list of customers!");
            System.out.println("------------------------------");
            return null;
        }
        else {
            int i = 1;
            System.out.println("*************************************************");
            System.out.println("Please select a customer from the following list:");
            System.out.println("*************************************************");
            for (DTOprintCustomer customer : dtoAllCustomers.getAllCustomersToPrint()) {
                System.out.println(i + ". Name: " + customer.getName() + ", Balance: " + customer.getBalance());
                i++;
            }
            System.out.println("(enter his number)");
            i = menus.giveInt();
            return dtoAllCustomers.getAllCustomersToPrint().get(i -1);
        }
    }

    public void promotingYazAndProvidingPayments(DTOprintAllCustomers printAllCustomers) {
        System.out.println("***************************************************");
        System.out.println("We were in yaz " + engine.getCurrentYaz().getCurrentYaz() + ", and now we are in yaz " + (engine.getCurrentYaz().getCurrentYaz() + 1));
        System.out.println("***************************************************");
        System.out.println();
        engine.promoteYaz();
        engine.providingPayments();

    }

}


