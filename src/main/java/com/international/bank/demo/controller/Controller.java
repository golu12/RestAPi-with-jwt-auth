package com.international.bank.demo.controller;

import com.international.bank.demo.model.*;
import com.international.bank.demo.security.MyUserDetailsService;
import com.international.bank.demo.service.AccountService;
import com.international.bank.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import javax.validation.Valid;

@RestController
public class Controller {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticateToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = myUserDetailsService
                .loadUserByUsername(authenticationRequest.getUserName());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @RequestMapping("/hello" )
    public String firstPage() {
        return "Hello World";
    }
    /**
    * Creating First Saving Account For Customer.
    * @Param savingAccount
    * @return
    */
    @PostMapping("/accounts/saving")
    public ResponseEntity createAccount(@Valid @RequestBody CustomerRequest customerRequest){
        Optional<Client> response = accountService.addSavingsAccount(customerRequest.getClient());
        return new ResponseEntity(response, HttpStatus.OK);
    }
    
    /**
    * Creating Current Account Based On Existing Account and Transfer Initial Credit.
    * @Param custId
    * @Param initialCredit
    * @return 
    */
    @GetMapping(path = "/accounts/current", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionResponse> addCurrentAccount(@RequestParam int custId , @RequestParam double initialCredit){
        Optional<Client> response = accountService.searchAndCreateCurrentAccount(custId, initialCredit);
        return new ResponseEntity(response, HttpStatus.OK);
    }
    
    /**
    * Fetch Customer Info For Current Account.
    * @Param custId
    * @return 
    */
    @GetMapping("accounts/customerInfo")
    public ResponseEntity getCustomerDetails(@RequestParam int custId){
        Optional<Client> response = accountService.getCustomerInformationOfNewAccount(custId);
        return new ResponseEntity(response, HttpStatus.OK);
    }


}
