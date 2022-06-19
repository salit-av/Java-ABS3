package DTO.Loan;

import AllParticipants.Loan.*;

import java.util.ArrayList;
import java.util.List;

public class DTOprintAllLoans {
    private List<DTOprintLoan> allLoansToPrint;

    public DTOprintAllLoans(Loans allLoans) {
        allLoansToPrint = new ArrayList<>();
        for(String name: allLoans.getLoans().keySet()){
            Loan loan = allLoans.getLoans().get(name);
            DTOprintLoan lo = new DTOprintLoan(loan.getId(), loan.getOwner(), loan.getCategory(), loan.getCapitalAtStart(), loan.getInterestPerPayment(), loan.getPaysEveryYaz(),
                                                    loan.getStatus(), loan.getLendersToDTO(), loan.getPayLeftFromPendingToActive(), loan.getYazFromPendingToActive(), loan.getNextYazToPay(),
                                                    loan.getCountAllUnpaidPayments(), loan.getTotalAllUnpaidPayments(), loan.getYazAtFirst(),
                                                    loan.getYazAtTheEnd(), loan.getCapitalAndInterest(), loan.getTotalYazTime(), loan.getNextPayIncludesInterestAndCapital());
            allLoansToPrint.add(lo);
        }
    }

    public List<DTOprintLoan> getAllLoansToPrint() {
        return allLoansToPrint;
    }

    public boolean isEmpty(){ return allLoansToPrint.isEmpty(); }
}
