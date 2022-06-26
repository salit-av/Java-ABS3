package DTO.Loan;

import AllParticipants.Loan.Loan;
import AllParticipants.Loan.Loans;

import java.util.ArrayList;
import java.util.List;

public class DTOLoans {
    private List<DTOLoan> DTOAllLoans;

    public DTOLoans(Loans allLoans) {
        DTOAllLoans = new ArrayList<>();
        for(String name: allLoans.getLoans().keySet()){
            Loan lo = allLoans.getLoans().get(name);
            DTOLoan loan = new DTOLoan(lo.getId(), lo.getCategory(), lo.getOwner(), lo.getNumOfOpenLoans(), lo.getTotalYazTime(), lo.getCapitalAtStart(), lo.getPaysEveryYaz(),
                    lo.getInterestPerPayment(), lo.getCapitalAndInterestAtStart(), lo.getStatus(), lo.getLendersToDTO(), lo.getPayLeftFromPendingToActive(), lo.getNextYazToPay(), lo.getNextPayIncludesInterestAndCapital(),
                    lo.getCountAllUnpaidPayments(), lo.getTotalAllUnpaidPayments(), lo.getYazAtFirst(), lo.getYazAtTheEnd());
            DTOAllLoans.add(loan);
        }
    }

    public List<DTOLoan> getDTOAllLoans() {
        return DTOAllLoans;
    }

    public boolean isEmpty(){ return DTOAllLoans.isEmpty(); }
}
