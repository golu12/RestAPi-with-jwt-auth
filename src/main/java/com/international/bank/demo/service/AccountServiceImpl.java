package com.international.bank.demo.service;

import com.international.bank.demo.model.Account;
import com.international.bank.demo.model.Client;
import com.international.bank.demo.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.international.bank.demo.Exception.ResourceNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;

@Component
public class AccountServiceImpl implements AccountService {
    @Autowired
    private ClientRepository clientRepository;

    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);


    /**
     * Below Method Will Create First Saving Account For Creation Of CurrentAccount.
     * @param client
     * @return 
     */
    @Override
    public Optional<Client> addSavingsAccount(Client client) {
      try {
    		if(clientRepository != null && client != null){
                Client result = clientRepository.save(client);
                if(result != null){
                    return Optional.of(result);
                }
            }
    	}catch (ResourceNotFoundException e) {
			LOG.error("Error while creating saving account", e.getAccountObject());
		}
        return Optional.empty();
    }
    
    /**
     * Below Method Will Search If Saving Account Exists, If Yes Then Create New Account Also If InitialCreadit is
     * Non Zero Then Transfer The Amount.
     * @param custId
     * @param initialCredit
     * @return
     */
    @Override
    public Optional<Client> searchAndCreateCurrentAccount(int custId, double initialCredit) {
        Client client = null;
        Account account = null;
        try{
        List<Client> existingAccountDetails = clientRepository.getCustomerRecord(custId);
        if(!existingAccountDetails.get(0).getAccounts().get(0).getAccountNumber().isEmpty()){
	    client = createRequest(existingAccountDetails,initialCredit,account,client);
            return addSavingsAccount(client);
        }

        }catch(ResourceNotFoundException e){
         LOG.error("Error while creating current account", e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * Method Will Result The CustomerInfo Of Current Account Which is Created.
     * @param custId
     * @return
     */
    @Override
    public Optional<Client> getCustomerInformationOfNewAccount(int custId) {
        
      	try {
    		if(clientRepository != null){
                Client result = clientRepository.getCustomerDetails(custId);
                if(result != null){
                    return Optional.of(result);
                }
            }
    	}catch(ResourceNotFoundException e) {
    		LOG.error("Error while fetching account details", e.getMessage());
    	}
        return Optional.empty();
    }
	
    @NotNull
    private Client createRequest(List<Client> existingAccountDetails, double initialCredit, Account account, Client client){
	account = new Account();
	client = new Client();
	client.setId(existingAccountDetails.get(0).getId());
		client.setCustomerId(existingAccountDetails.get(0).getCustomerId());
		client.setFirstName(existingAccountDetails.get(0).getFirstName());
		client.setLastName(existingAccountDetails.get(0).getLastName());
		client.setAddress(existingAccountDetails.get(0).getAddress());
		client.setBranch(existingAccountDetails.get(0).getBranch());
		client.setEmailId(existingAccountDetails.get(0).getEmailId());
		client.setIfscCode(existingAccountDetails.get(0).getIfscCode());
		account.setAccountNumber("CAP089039458");
		account.setAccountType("current");
		if(initialCredit > 0){
		    account.setBalance(initialCredit);
		}else {
		    account.setBalance(0);
		}
		account.setCustomerId(existingAccountDetails.get(0).getAccounts().get(0).getCustomerId());
		account.setPid(904);
		client.setAccounts(Collections.singletonList(account));
		return client;
    }
}
