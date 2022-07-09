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
            DTOLoan loan = new DTOLoan(lo);
            DTOAllLoans.add(loan);
        }
    }

    public DTOLoans(List<Loan> allLoans) {
        DTOAllLoans = new ArrayList<>();
        for(Loan loan: allLoans){
            DTOAllLoans.add(new DTOLoan(loan));
        }
    }

    public List<DTOLoan> getDTOAllLoans() {
        return DTOAllLoans;
    }

    public boolean isEmpty(){ return DTOAllLoans.isEmpty(); }
}
