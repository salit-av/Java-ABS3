package DTO;

import DTO.Customers.DTOprintAllCustomers;
import DTO.Loan.DTOLoan;
import DTO.Loan.DTOprintAllLoans;

import java.util.List;

public class DTOactivate {
    private DTOprintAllCustomers dtoAllCustomers;
    private List<DTOLoan> dtoAllLoans;
    private DTOCategories dtoAllCategories;

    public DTOactivate(DTOprintAllCustomers dtoAllCustomers, List<DTOLoan> dtoAllLoans, DTOCategories dtoAllCategories) {
        this.dtoAllCustomers = dtoAllCustomers;
        this.dtoAllLoans = dtoAllLoans;
        this.dtoAllCategories = dtoAllCategories;
    }

    public DTOprintAllCustomers getDtoAllCustomers() {
        return dtoAllCustomers;
    }

    public List<DTOLoan> getDtoAllLoans() {
        return dtoAllLoans;
    }

    public DTOCategories getDtoAllCategories() {
        return dtoAllCategories;
    }
}
