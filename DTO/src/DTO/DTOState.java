package DTO;

import DTO.Customers.DTOCustomers;
import DTO.Loan.DTOLoans;

public class DTOState {
    private DTOLoans dtoLoans;
    private DTOCustomers dtoCustomers;

    public DTOState(DTOLoans dtoLoans, DTOCustomers dtoCustomers) {
        this.dtoLoans = dtoLoans;
        this.dtoCustomers = dtoCustomers;
    }

    public DTOLoans getDtoLoans() {
        return dtoLoans;
    }

    public DTOCustomers getDtoCustomers() {
        return dtoCustomers;
    }
}
