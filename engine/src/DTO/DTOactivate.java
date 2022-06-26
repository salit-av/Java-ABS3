package DTO;

import DTO.Customers.DTOCustomers;
import DTO.Loan.DTOLoan;

import java.util.List;

public class DTOactivate {
    private DTOCustomers dtoAllCustomers;
    private List<DTOLoan> dtoAllLoans;
    private DTOCategories dtoAllCategories;

    public DTOactivate(DTOCustomers dtoAllCustomers, List<DTOLoan> dtoAllLoans, DTOCategories dtoAllCategories) {
        this.dtoAllCustomers = dtoAllCustomers;
        this.dtoAllLoans = dtoAllLoans;
        this.dtoAllCategories = dtoAllCategories;
    }

    public DTOCustomers getDtoAllCustomers() {
        return dtoAllCustomers;
    }

    public List<DTOLoan> getDtoAllLoans() {
        return dtoAllLoans;
    }

    public DTOCategories getDtoAllCategories() {
        return dtoAllCategories;
    }
}
