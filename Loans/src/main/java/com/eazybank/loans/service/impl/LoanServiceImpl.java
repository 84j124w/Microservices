package com.eazybank.loans.service.impl;

import com.eazybank.loans.constants.LoansContants;
import com.eazybank.loans.dto.LoansDto;
import com.eazybank.loans.entity.Loans;
import com.eazybank.loans.exception.LoanAlreadyExistsException;
import com.eazybank.loans.exception.ResourceNotFoundException;
import com.eazybank.loans.mapper.LoansMapper;
import com.eazybank.loans.repository.LoansRepository;
import com.eazybank.loans.service.ILoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements ILoanService {
    private LoansRepository loansRepository;

    /**
     * @param mobileNumber - Mobile number of the customer, to create loan
     */
    @Override
    public void createLoan(String mobileNumber) {
        Optional<Loans> loan = loansRepository.findByMobileNumber(mobileNumber);
        if(loan.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobile number: "+mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    /**
     * @param mobileNumber - mobile number of the customer
     * @return loan - return the new loan details
     * */
    private Loans createNewLoan(String mobileNumber){
        Loans loan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        loan.setLoanNumber(Long.toString(randomLoanNumber));
        loan.setMobileNumber(mobileNumber);
        loan.setLoanType(LoansContants.HOME_LOAN);
        loan.setTotalLoan(LoansContants.NEW_LOAN_LIMIT);
        loan.setAmountPaid(0);
        loan.setOutstandingAmount(LoansContants.NEW_LOAN_LIMIT);
        return loan;
    }

    /**
     * @param mobileNumber - input mobile number
     * @return loans details based on mobile number
     */
    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );

        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    /**
     * @param loansDto - LoansDto object
     * @return boolean indicating if the update of loan details is successful or not
     */
    @Override
    public boolean updateLoan(LoansDto loansDto) {
       Loans loans = loansRepository.findByMobileNumber(loansDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobile", loansDto.getMobileNumber())
        );
       LoansMapper.mapToLoans(loansDto, loans);
       loansRepository.save(loans);

        return true;
    }

    /**
     * @param mobileNumber - input mobile number
     * @return boolean indicating if the delete of loan details is successful or not
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobile", mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }

}
