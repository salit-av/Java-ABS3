package AllParticipants.Loan;

import AllParticipants.Customer.Customer;
import DTO.Loan.DTOLoan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Loans {
    private Map<String, Loan> loans;  // String - id of loan
    public Loans() {
        this.loans = new HashMap<>();
    }

    public Map<String, Loan> getLoans() {
        if (loans == null) {
            loans = new HashMap<>();
        }
        return this.loans;
    }

    public boolean addLoanFromAbs(Loan loan) {
        Loan lo = loans.get(loan.getId());
        if (lo == null) {
            loans.put(loan.getId(), loan);
            return true;
        }
        return false;
    }

    public void setOwnerCus(Customer ownerCus) {
        for (String id : loans.keySet()) {
            Loan loan = loans.get(id);
            loan.setOwnerCus(ownerCus);
        }
    }

    public Loan fromDTOLoanToLoan(DTOLoan dtoLoan){
       return loans.get(dtoLoan.getId());
    }


    public void saleLoan(Customer lender, String loanID) {
        loans.get(loanID).saleLoan(lender);
    }

    public List<DTOLoan> getDTOLoansInBuyingList() {
        List<DTOLoan> res = new ArrayList<>();
        for(Loan loan:loans.values()){
            if(loan.isLoanInBuyingList()){
                res.add(new DTOLoan(loan));
            }
        }
        return res;
    }
}





