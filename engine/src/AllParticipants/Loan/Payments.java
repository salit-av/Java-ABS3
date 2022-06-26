package AllParticipants.Loan;

import DTO.Loan.DTOpayment;

import java.util.ArrayList;
import java.util.List;

public class Payments {
    private List<Payment> allPayments;
    private int investment; // Customer's investment
    private int capital;  // Keren until now
    private int interest;  // Ribit until now
    private int capitalEveryPay;
    private int interestEveryPay;
    private int countUnpaidPayments; // how many unpaid for customer
    private int totalUnpaidPayments; // unpaid money for customer
    private int interestAndCapitalEveryPay; //amount needs to pay every yaz
    private int totalInterestAndCapitalUntilNow;
    private int capitalAndInterestAtStart;
    private int totalYazTime;

    public Payments(int investment, int interest, int totalYazTime, int numberOfPayments) {
        this.allPayments = new ArrayList<>();
        this.investment = investment;
        this.capital = 0;
        this.interest = 0;
        this.capitalEveryPay = investment / numberOfPayments;
        this.interestEveryPay = ((investment * interest) / 100) /numberOfPayments;
        this.countUnpaidPayments = 0;
        this.totalUnpaidPayments = 0;
        this.interestAndCapitalEveryPay = this.capitalEveryPay + this.interestEveryPay;
        this.totalInterestAndCapitalUntilNow = 0;
        this.capitalAndInterestAtStart = investment + (investment * interest) / 100;
        this.totalYazTime = totalYazTime;
    }

    public List<Payment> getAllPayments() {
        return allPayments;
    }

    public int getInvestment() {
        return investment;
    }

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public int getInterest() {
        return interest;
    }

    public void setInterest(int interest) {
        this.interest = interest;
    }

    public int getCapitalEveryPay() {
        return capitalEveryPay;
    }

    public int getInterestEveryPay() {
        return interestEveryPay;
    }

    public int getCountUnpaidPayments() {
        return countUnpaidPayments;
    }

    public void setCountUnpaidPayments(int countUnpaidPayments) {
        this.countUnpaidPayments = countUnpaidPayments;
    }

    public int getTotalUnpaidPayments() {
        return totalUnpaidPayments;
    }

    public void setTotalUnpaidPayments(int totalUnpaidPayments) {
        this.totalUnpaidPayments = totalUnpaidPayments;
    }

    public int getInterestAndCapitalEveryPay() {
        return interestAndCapitalEveryPay;
    }

    public int getTotalInterestAndCapitalUntilNow() {
        return totalInterestAndCapitalUntilNow;
    }

    public void setTotalInterestAndCapitalUntilNow(int totalInterestAndCapitalUntilNow) {
        this.totalInterestAndCapitalUntilNow = totalInterestAndCapitalUntilNow;
    }

    public int getCapitalToPay() {
        return investment - capital;
    }

    public int getInterestToPay() {
        return capitalAndInterestAtStart - investment - interest;
    }

    public List<DTOpayment> fromPaymentsToDTO(){
        List<DTOpayment> res = new ArrayList<>();
        for(Payment payment: allPayments){
            res.add(new DTOpayment(payment.getYazPayment(), payment.getInterestPerPayment(), payment.getCapitalPerPayment(), payment.getTotalPayment()));
        }
        return res;
    }
}
