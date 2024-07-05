package com.eazybank.loans.controller;

import com.eazybank.loans.constants.LoansContants;
import com.eazybank.loans.dto.LoansDto;
import com.eazybank.loans.dto.ResponseDto;
import com.eazybank.loans.service.ILoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class LoansController {
    private ILoanService iLoanService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam String mobileNumber){
        iLoanService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoansContants.STATUS_201, LoansContants.MESSAGE_201));

    }

    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam String mobileNumber){
        LoansDto loansDto = iLoanService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoanDetails(@RequestBody LoansDto loansDto){
        boolean isUpdated = iLoanService.updateLoan(loansDto);
        if(isUpdated){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(LoansContants.STATUS_200, LoansContants.MESSAGE_200));
        }

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(LoansContants.STATUS_417, LoansContants.MESSAGE_417_UPDATE));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoanDetails(@RequestParam String mobileNumber){
        boolean isDeleted = iLoanService.deleteLoan(mobileNumber);
        if(isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(LoansContants.STATUS_200, LoansContants.MESSAGE_200));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDto(LoansContants.STATUS_417 ,LoansContants.MESSAGE_417_DELETE));
    }
}
