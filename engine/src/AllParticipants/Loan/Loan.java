package AllParticipants.Loan;

import AllParticipants.Customer.Customer;
import AllParticipants.Customer.Transaction;
import AllParticipants.Notification;
import DTO.Loan.DTOpayments;
import Engine.Yaz;
import Status.Status;

import java.util.HashMap;
import java.util.Map;

public class Loan implements Cloneable{
    private String id;
    private String owner;
    //private Customer ownerCus;
    private String category;
    private int capitalAtStart; // Money at start
    private int interestAtStart;
    private int capitalNow; // Money now after payments
    private int investmentInLoan; // how much money left to add
    private int interestPerPayment; // Ribit percent
    private int paysEveryYaz; // How often is the loan paid off in yaz
    private  int numberOfPayments;
    private Status status;

    // pending
    private Map<Customer, Payments> lenders;
    private int payLeftFromPendingToActive;

    // active
    private int yazFromPendingToActive; // how many yaz from pending to active
    private int nextYazToPay; // what yaz is the next payment
    //more at payment

    // finished
    private int yazAtFirst;
    private int yazAtTheEnd;

    // more info
    private int capitalAndInterest; // at the beginning --> capitalAtStart + (capitalAtStart * interestPerPayment) \ 100
    private int interestAndCapitalEveryPay;
    private int totalInterestAndCapitalUntilNow;
    private int capitalAndInterestAtStart;
    private int totalYazTime; // how long the lone is active
    private int nextPayIncludesInterestAndCapital; //  capitalAndInterest \ (totalYazTime \ paysEveryYaz)
    private int countAllUnpaidPayments; // how many unpaid for customer
    private int totalAllUnpaidPayments; // unpaid money for customer

    public Loan(String id, String owner, String category, int capital, int totalYazTime, int paysEveryYaz, int interestPerPayment) {
        this.id = id;
        this.owner = owner;
        //this.ownerCus = ownerCus;
        this.category = category;
        this.status = Status.NEW;
        this.capitalAtStart = capital;
        this.capitalNow = capital;
        this.interestAtStart = (capitalAtStart * interestPerPayment) / 100;
        this.investmentInLoan = 0;
        this.capitalAndInterest = capitalAtStart + (capitalAtStart * interestPerPayment) / 100;
        this.totalYazTime = totalYazTime;
        this.paysEveryYaz = paysEveryYaz;
        this.nextYazToPay = paysEveryYaz;
        this.interestPerPayment = interestPerPayment;
        this.yazFromPendingToActive = 0;
        this.nextPayIncludesInterestAndCapital = 0;
        this.yazAtFirst = 1;
        this.yazAtTheEnd = 0;
        this.lenders = new HashMap<>();
        this.payLeftFromPendingToActive = capital;
        this.countAllUnpaidPayments = 0;
        this.totalAllUnpaidPayments = 0;
        this.numberOfPayments = totalYazTime / paysEveryYaz;
        this.interestAndCapitalEveryPay = (capitalAtStart + interestAtStart) / numberOfPayments;
        this.totalInterestAndCapitalUntilNow = 0;
        this.capitalAndInterestAtStart = capitalAtStart + interestAtStart;
    }

    public void setNotificationsAtLenders(){
        for(Customer lender: lenders.keySet()){
            lender.getNotificationsList().add(new Notification("PayYou!", owner + " should pay you for a loan in id:" + id, "how fun!"));
        }
}
    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String value) {
        this.owner = value;
    }

    /*public Customer getOwnerCus() {
        return ownerCus;
    }*/

    public void setOwnerCus(Customer ownerCus) {
       // this.ownerCus = ownerCus;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String value) {
        this.category = value;
    }

    public int getCapitalAtStart() {
        return capitalAtStart;
    }

    public void setCapitalAtStart(int value) {
        this.capitalAtStart = value;
    }

    public int getCapitalNow() {
        return capitalNow;
    }

    public void setCapitalNow(int value) {
        this.capitalNow = value;
    }

    public int getInvestmentInLoan() {
        return investmentInLoan;
    }

    public void setInvestmentInLoan(int investmentInLoan) {
        this.investmentInLoan = investmentInLoan;
    }

    public int getInterestPerPayment() {
        return interestPerPayment;
    }

    public void setInterestPerPayment(int value) {
        this.interestPerPayment = value;
    }

    public int getPaysEveryYaz() {
        return paysEveryYaz;
    }

    public void setPaysEveryYaz(int value) {
        this.paysEveryYaz = value;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Map<Customer, Payments> getLenders() {
        return lenders;
    }

    public Map<String, DTOpayments> getLendersToDTO(){
        Map<String, DTOpayments> res = new HashMap<>();
        for(Customer customer: lenders.keySet()){
            Payments payForCus = lenders.get(customer);
            DTOpayments newPay = new DTOpayments(payForCus.fromPaymentsToDTO(), payForCus.getInvestment(),payForCus.getCapitalToPay(),
                    payForCus.getInterestToPay(), payForCus.getCapital(), payForCus.getInterest(), payForCus.getCapitalToPay(), payForCus.getInterestToPay(), payForCus.isInBuyingList());
            res.put(customer.getName(), newPay);
        }
        return res;
    }

    public void setLenders(Map<Customer, Payments> lenders) {
        this.lenders = lenders;
    }

    public int getPayLeftFromPendingToActive() {
        return payLeftFromPendingToActive;
    }

    public void setPayLeftFromPendingToActive(int payLeftFromPendingToActive) {
        this.payLeftFromPendingToActive = payLeftFromPendingToActive;
    }

    public int getYazFromPendingToActive() {
        return yazFromPendingToActive;
    }

    public void setYazFromPendingToActive(int yazFromPendingToActive) {
        this.yazFromPendingToActive = yazFromPendingToActive;
    }

    public int getNextYazToPay() {
        return nextYazToPay;
    }

    public boolean doPayThisYaz(int currentYaz){
        if(yazFromPendingToActive != 0 && (currentYaz - yazFromPendingToActive) % (paysEveryYaz) == 0){
            return true;
        }
        return false;
    }

    public int getYazAtFirst() {
        return yazAtFirst;
    }

    public int getYazAtTheEnd() {
        return yazAtTheEnd;
    }

    public int getCapitalAndInterest() {
        return capitalAndInterest;
    }

    public int getCapitalAndInterestAtStart() {
        return capitalAndInterestAtStart;
    }

    public int getInterestAndCapitalEveryPay() {
        return interestAndCapitalEveryPay;
    }

    public int getTotalInterestAndCapitalUntilNow() {
        return totalInterestAndCapitalUntilNow;
    }

/*    public int getNumOfOpenLoans() {
        return ownerCus.getLoansAsBorrower().getLoans().size();
    }*/
    public int getTotalYazTime() {
        return totalYazTime;
    }

    public void setTotalYazTime(int value) {
        this.totalYazTime = value;
    }

    public void setCountAllUnpaidPayments(int count){
        this.countAllUnpaidPayments = count;
    }

    public void setTotalAllUnpaidPayments(int totalAllUnpaidPayments) {
        this.totalAllUnpaidPayments = totalAllUnpaidPayments;
    }

    public int getNextPayIncludesInterestAndCapital() {
        int pay = 0;
        if(lenders.isEmpty()){
            return nextPayIncludesInterestAndCapital;
        }
        else{
            for(Customer customer:lenders.keySet()){
                Payments payments = lenders.get(customer);
                pay += ((payments.getCapitalEveryPay() + payments.getInterestEveryPay()));
            }
        }
        return pay;
    }

    public int getCountAllUnpaidPayments() {
        return countAllUnpaidPayments;
    }

    public int getTotalAllUnpaidPayments() {
        return totalAllUnpaidPayments;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this){
            return true;
        }

        if (!(obj instanceof Loan)) {
            return false;
        }

        final Loan other =(Loan) obj;

        return this.capitalNow == other.capitalNow;
    }

    public void updateLoanAfterInvestment(LoanEditor loanEditor, Yaz currentYaz) {
        if(status.equals(Status.NEW)){
            status = Status.PENDING;
        }
        investmentInLoan +=  loanEditor.getInvestment();
        payLeftFromPendingToActive -= loanEditor.getInvestment();

        if(payLeftFromPendingToActive == 0){
            status = Status.ACTIVE;
            yazFromPendingToActive = currentYaz.getCurrentYaz();
            nextYazToPay += currentYaz.getCurrentYaz();
        }

    }

    public void addToLenders(Customer customer, LoanEditor loanEditor) {
        Payments isInPay = lenders.get(customer.getName());
        if(isInPay == null){
            lenders.put(customer, new Payments(loanEditor.getInvestment(), interestPerPayment, totalYazTime, numberOfPayments));
        }
        else{
            isInPay = new Payments(isInPay.getInvestment() + loanEditor.getInvestment(), (loanEditor.getInvestment()*interestPerPayment) / 100 / (totalYazTime/paysEveryYaz), totalYazTime, numberOfPayments);
        }

    }

    public void payLenders(Customer owner, int currentYaz) {
        Payments paymentsOfCus;
        for(Customer lender: lenders.keySet()){
            paymentsOfCus = lenders.get(lender);
            int totalPay = paymentsOfCus.getInterestAndCapitalEveryPay();
            // add payment to customers payments
            paymentsOfCus.setCapital(paymentsOfCus.getCapital() + paymentsOfCus.getCapitalToPay());
            paymentsOfCus.setInterest(paymentsOfCus.getInterest() + paymentsOfCus.getInterestToPay());
            paymentsOfCus.setTotalInterestAndCapitalUntilNow(getTotalInterestAndCapitalUntilNow() + totalPay);
            paymentsOfCus.getAllPayments().add(new Payment(currentYaz, paymentsOfCus.getInterestEveryPay(), paymentsOfCus.getCapitalEveryPay(), totalPay));

            // add to lender
            lender.addToTransactions(new Transaction(currentYaz, totalPay, '+', lender.getBalance(), lender.getBalance() + totalPay));
            lender.setBalance(lender.getBalance() + totalPay);
            // add to owner
            owner.addToTransactions(new Transaction(currentYaz, totalPay, '-', owner.getBalance(), owner.getBalance() - totalPay));
            owner.setBalance(owner.getBalance() - totalPay);

            // Change details of the loan
            totalInterestAndCapitalUntilNow += totalPay;
            nextYazToPay += paysEveryYaz;


            if(currentYaz >= totalYazTime && totalInterestAndCapitalUntilNow == capitalAndInterestAtStart && countAllUnpaidPayments == 0){
                status = Status.FINISHED;
                nextYazToPay = 0;
                // nextPayIncludesInterestAndCapital = 0;  TODO check if not in mark
                yazAtTheEnd = currentYaz;
            }
        }
    }

    public void addUnpaidPayment(Customer customer, int currentYaz) {
        Payments paymentsOfCus;
        for (Customer lender : lenders.keySet()) {
            paymentsOfCus = lenders.get(lender);
            int totalPay = paymentsOfCus.getInterestAndCapitalEveryPay();
            // add payment to customers payments
            paymentsOfCus.getAllPayments().add(new Payment(currentYaz, paymentsOfCus.getInterestEveryPay(), paymentsOfCus.getCapitalEveryPay(), 0));
            paymentsOfCus.setCountUnpaidPayments(paymentsOfCus.getTotalUnpaidPayments() + 1);
            paymentsOfCus.setTotalUnpaidPayments(paymentsOfCus.getTotalUnpaidPayments() + totalPay);


            // Change details of the loan
            status = Status.RISK;
            countAllUnpaidPayments++;
            totalAllUnpaidPayments += totalPay;
            //nextPayIncludesInterestAndCapital += totalPay;
            nextYazToPay += paysEveryYaz;
        }
    }


    public void payLendersAllPay(Customer borrower, int currentYaz) {
        Payments paymentsOfCus;
        for(Customer lender: lenders.keySet()) {
            paymentsOfCus = lenders.get(lender);
            int totalPay = paymentsOfCus.getCapitalToPay() + paymentsOfCus.getInterestToPay();
            // add payment to customers payments
            paymentsOfCus.setCapital(paymentsOfCus.getCapital() + paymentsOfCus.getCapitalEveryPay());
            paymentsOfCus.setInterest(paymentsOfCus.getInterest() + paymentsOfCus.getInterestEveryPay());
            paymentsOfCus.setTotalInterestAndCapitalUntilNow(getTotalInterestAndCapitalUntilNow() + totalPay);
            paymentsOfCus.getAllPayments().add(new Payment(currentYaz, paymentsOfCus.getInterestToPay(), paymentsOfCus.getCapitalToPay(), totalPay));

            // add to lender
            lender.addToTransactions(new Transaction(currentYaz, totalPay, '+', lender.getBalance(), lender.getBalance() + totalPay));
            lender.setBalance(lender.getBalance() + totalPay);
            // add to owner
            borrower.addToTransactions(new Transaction(currentYaz, totalPay, '-', borrower.getBalance(), borrower.getBalance() - totalPay));
            borrower.setBalance(borrower.getBalance() - totalPay);

            // Change details of the loan
            totalInterestAndCapitalUntilNow += totalPay;
            nextYazToPay += paysEveryYaz;

            status = Status.FINISHED;
            nextYazToPay = 0;
            nextPayIncludesInterestAndCapital = 0;
            totalAllUnpaidPayments = 0;
            countAllUnpaidPayments = 0;
            yazAtTheEnd = currentYaz;

        }
    }


    public int getInterestAtStart() {
        return interestAtStart;
    }

    public int getNumberOfPayments() {
        return numberOfPayments;
    }

    public boolean isRiskOrActive() {
       return status.equals(Status.RISK) || status.equals(Status.ACTIVE);
    }

    public boolean isLoanInBuyingList(){
        for(Payments payments: lenders.values()){
            if(payments.isInBuyingList()){
                return true;
            }
        }
        return false;
    }

    public void removeFromBuyingList() {
        for (Customer lender : lenders.keySet()) {
            Payments payments = lenders.get(lender);
            if (payments.isInBuyingList()) {
                lender.getNotificationsList().add(new Notification("Attention!", id, "remove from buying list"));
                payments.setIsInBuyingList(false);
            }
        }
    }

    public void saleLoan(Customer lender) {
        lenders.get(lender).setIsInBuyingList(true);
    }

    @Override
    public Loan clone()  {
       /* try {
            Loan newLoan = (Loan) super.clone();
            //newLoan.setOwnerCus(newLoan.getOwnerCus().clone());

            Map<Customer, Payments> newLenders = new HashMap<>();
            for(Customer customer: newLoan.getLenders().keySet()){
                Payments payments = newLoan.getLenders().get(customer);
                newLenders.put(customer.clone(), payments.clone());
            }
            newLoan.setLenders(newLenders);

            return newLoan;
        } catch (CloneNotSupportedException ex) {
            return null;
        }*/
        return null;
    }
}







