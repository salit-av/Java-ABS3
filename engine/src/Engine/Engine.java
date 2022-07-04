package Engine;

import AllParticipants.Customer.Customer;
import AllParticipants.Descriptor;
import AllParticipants.Loan.Loan;
import AllParticipants.Loan.LoanEditor;
import AllParticipants.Loan.Loans;
import DTO.Loan.*;
import DTO.Customers.*;
import DTO.*;
import Exceptions.*;
import Status.Status;

import java.util.*;
import java.util.stream.Collectors;

public class Engine {
    private Descriptor descriptor;
    private Yaz currentYaz;


    public Engine() {
        this.descriptor = new Descriptor();
        this.currentYaz = new Yaz();
    }


    public Yaz getCurrentYaz() {
        return currentYaz;
    }

    public void loadFromXML(String path, String customerName) throws referenceToCategoryThatIsntDefinedException, loansWithTheSameNameException, paymentRateIncorrectException {
        descriptor.loadFromXML(path, customerName);
    }

    public DTOLoans printAllLoans(){
        return new DTOLoans(descriptor.getAllLoans());
    }

    public DTOCustomers printAllCustomers(){
        return new DTOCustomers(descriptor.getAllCustomers());
    }

    public void addBalanceToCustomer(DTOBalace dtoBalace) {
        descriptor.addBalanceToCustomer(dtoBalace, currentYaz);
    }

    public void removeBalanceToCustomer(DTOBalace dtoBalace) {
        descriptor.removeBalanceToCustomer(dtoBalace, currentYaz);
    }

    public DTOactivate activate() {
        DTOCustomers dtoAllCustomers = new DTOCustomers(descriptor.getAllCustomers());
        List<DTOLoan> dtoAllLoans =  fromLoansToDTO(descriptor.getAllLoans());
        DTOCategories dtoAllCategories = new DTOCategories(descriptor.getAllCategories().getCategories());
        DTOactivate dtoActivate = new DTOactivate(dtoAllCustomers, dtoAllLoans, dtoAllCategories);

        return dtoActivate;
    }

    public List<DTOLoan> fromLoansToDTO(Loans allLoans) {
        List<DTOLoan> res = new ArrayList<>();
        Loan loan;
        for(String id: allLoans.getLoans().keySet()){
            loan = allLoans.getLoans().get(id);
            res.add(new DTOLoan(loan));
        }
        return res;
    }

    public Customer fromDTOcustomerToCustomer(DTOCustomer dtoCustomer) {
        return descriptor.getAllCustomers().getCustomers().get(dtoCustomer.getName());
    }

    public List<DTOLoan> filterAllInvestmentLoans(List<DTOLoan> dtoAllLoans, List<String> filterCategories, int minimumInterest, int minimumYazForAllLoan, int maxLoansOpen, String cusName) {
        Customer customer = descriptor.getAllCustomers().getCustomers().get(cusName);

        if(maxLoansOpen > 0) {
            return dtoAllLoans.stream().filter(dtoLoan -> !dtoLoan.getOwnersName().equals(customer.getName())).
                    filter(dtoLoan -> dtoLoan.getStatus().equals(Status.PENDING) ||dtoLoan.getStatus().equals(Status.NEW)).
                    filter(dtoLoan -> isInLoansList(dtoLoan.getCategory(), filterCategories)).
                    filter(dtoLoan -> dtoLoan.getInterestPerPayment() >= minimumInterest).
                    filter(dtoLoan -> dtoLoan.getNumOfOpenLoans() <= maxLoansOpen).
                    filter(dtoLoan -> dtoLoan.getTotalYazTime() >= minimumYazForAllLoan).collect(Collectors.toList());
        }
        else{
            return dtoAllLoans.stream().filter(dtoLoan -> !dtoLoan.getOwnersName().equals(customer.getName())).
                    filter(dtoLoan -> dtoLoan.getStatus().equals(Status.PENDING) ||dtoLoan.getStatus().equals(Status.NEW)).
                    filter(dtoLoan -> isInLoansList(dtoLoan.getCategory(), filterCategories)).
                    filter(dtoLoan -> dtoLoan.getInterestPerPayment() >= minimumInterest).
                    filter(dtoLoan -> dtoLoan.getTotalYazTime() >= minimumYazForAllLoan).collect(Collectors.toList());
        }
    }

    public boolean isInLoansList(String category, List<String> filterCategories) {
        if (filterCategories.isEmpty()) {
            return true;
        }
        else {
            for (String catego : filterCategories) {
                if (catego.equals(category)) {
                    return true;
                }
            }
            return false;
        }
    }

    public Map<String, DTOLoan> getAllInvestmentLoansAfterTheirSelection(Map<String, DTOLoan> allInvestmentLoans, List<Integer> numsOfLoans) {
        Map<String, DTOLoan> res = new HashMap<>();
        String name;
        DTOLoan dtoLoan;
        for(Integer i: numsOfLoans){
            name = allInvestmentLoans.keySet().stream().toArray()[i-1].toString();
            dtoLoan = allInvestmentLoans.get(name);
            res.put(dtoLoan.getId(), dtoLoan);
        }
        return res;
    }

    public void distributionOfMoneyForLoans(DTOCustomer dtoCustomer, int investmentBalance, Map<String, DTOLoan> allInvestmentLoans) {
        Customer customer = fromDTOcustomerToCustomer(dtoCustomer);
        List <LoanEditor> allInvestmentLoansSorted = sortAllInvestmentLoansByTheirCapital(allInvestmentLoans);
        List <LoanEditor> allInvestmentLoansAfterAlgorithm = fundingAlgorithm(allInvestmentLoansSorted, customer, investmentBalance);
        descriptor.updateLoansAndCustomersAfterFundingAlgorithm(allInvestmentLoansAfterAlgorithm, customer, currentYaz);

    }


    public List <LoanEditor> fundingAlgorithm(List<LoanEditor> allInvestmentLoansSorted, Customer customer, int investmentBalance) {
    List<LoanEditor> updateAllInvestmentLoans = new ArrayList<>();
    LoanEditor loanWithLeastMoney;
        int capitalForNow, totalPayment;
        while (investmentBalance > 0 && !allInvestmentLoansSorted.isEmpty()) {
            loanWithLeastMoney = allInvestmentLoansSorted.get(0);
            capitalForNow = loanWithLeastMoney.getCapitalNow();
            totalPayment = capitalForNow * allInvestmentLoansSorted.size();

            if (totalPayment > investmentBalance) {
                capitalForNow = investmentBalance / allInvestmentLoansSorted.size();
            }
            addToAllLoansPayment(allInvestmentLoansSorted, capitalForNow, updateAllInvestmentLoans);
            investmentBalance -= capitalForNow;
        }
        if(!allInvestmentLoansSorted.isEmpty()) {
            updateAllInvestmentLoans.addAll(allInvestmentLoansSorted);
        }
        return  updateAllInvestmentLoans;
    }

    public void addToAllLoansPayment(List<LoanEditor> allInvestmentLoansSorted, int capitalForNow, List<LoanEditor> updateAllInvestmentLoans) {
        for (LoanEditor loanEditor: allInvestmentLoansSorted) {
            loanEditor.setInvestment(loanEditor.getInvestment() + capitalForNow);
            loanEditor.setCapitalLeft(loanEditor.getCapitalNow() - loanEditor.getInvestment());
            if(loanEditor.getCapitalLeft() == 0) {
                updateAllInvestmentLoans.add(loanEditor);
                if(allInvestmentLoansSorted.size() == 1) {
                    allInvestmentLoansSorted.remove(loanEditor);
                    break;
                }
            }
        }
    }

    public List<LoanEditor> sortAllInvestmentLoansByTheirCapital(Map<String, DTOLoan> allInvestmentLoans) {
        List<LoanEditor> allInvestmentLoansSorted = new ArrayList<>();
        Loans allLoans = descriptor.getAllLoans();
        Loan loan;
        for(String loanId: allInvestmentLoans.keySet()){
            loan = allLoans.getLoans().get(loanId);
            allInvestmentLoansSorted.add(new LoanEditor(loanId, loan.getOwner(), loan.getCapitalNow()));
        }

        return allInvestmentLoansSorted.stream().sorted(Comparator.comparingInt(LoanEditor::getCapitalNow)).collect(Collectors.toList());
    }

    public void promoteYaz() {
        currentYaz.setCurrentYaz(currentYaz.getCurrentYaz() + 1);
    }

    public void providingPayments() {
        descriptor.providingPayments(currentYaz.getCurrentYaz());
    }

    public List<String> getCategories(){
        return descriptor.getAllCategories().getCategories();
    }

    public boolean payPaymentToLoan(DTOLoan loan, DTOCustomer borrower) {
        return descriptor.payPaymentToLoan(currentYaz.getCurrentYaz(), loan, borrower);
    }

    public boolean payAllLoan(DTOLoan loan, DTOCustomer borrower) {
        return descriptor.payAllLoan(currentYaz.getCurrentYaz(), loan, borrower);
    }

    public void setLoansWithPaymentsInCustomers() {
        descriptor.setLoansWithPaymentsInCustomers(currentYaz.getCurrentYaz());
    }

    public boolean isCustomerExists(String username) {
        return descriptor.isCustomerExists(username);
    }

    public void addCustomer(String username) {
        descriptor.addCustomer(username);
    }

    public boolean isAdminExists(String username) {
        return descriptor.isAdminExists(username);
    }

    public void addAdmin(String username) {
        descriptor.addAdmin(username);
    }

    public DTOLoans getCustomersLoansAsBorrower(String username) {
        return descriptor.getCustomersLoansAsBorrower(username);
    }

    public DTOLoans getCustomersLoansAsLender(String username) {
        return descriptor.getCustomersLoansAsLender(username);
    }

    public DTOLoan getDTOLoan(String id) {
        return descriptor.getDTOLoan(id);
    }

    public DTOCustomer getDTOCustomer(String username) {
        return printAllCustomers().findCustomer(username);
    }

    public String addLoan(String cusName, String id, String category, int capital, int totalYazTime, int paysEveryYaz, int internistPerPayment) {
        return descriptor.addLoan(cusName, id, category, capital, totalYazTime, paysEveryYaz, internistPerPayment);
    }

    public List<DTOtransaction> getCustomersTransactions(String username) {
        return descriptor.getCustomersTransactions(username);
    }
}
