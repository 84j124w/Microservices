package com.eazybank.loans.service;

import com.eazybank.loans.dto.LoansDto;
import com.eazybank.loans.entity.Loans;

public interface ILoanService {
    /**
     * @param mobileNumber - Mobile number of the customer, to create loan
     * */
    void createLoan(String mobileNumber);

    /**
     * @param mobileNumber - input mobile number
     * @return loans details based on mobile number*/
    LoansDto fetchLoan(String mobileNumber);

    /**
     * @param loansDto - LoansDto object
     * @return boolean indicating if the update of loan details is successful or not
     * */
    boolean updateLoan(LoansDto loansDto);

    /**
     * @param mobileNumber - input mobile number
     * @return boolean indicating if the delete of loan details is successful or not
     * */
    boolean deleteLoan(String mobileNumber);
}
