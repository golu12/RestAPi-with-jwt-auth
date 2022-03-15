package com.international.bank.demo;

import java.util.Collections;

import javax.validation.constraints.NotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.international.bank.demo.SpringBootApplication;
import com.international.bank.demo.model.Account;
import com.international.bank.demo.model.Client;
import com.international.bank.demo.model.CustomerRequest;

import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootApplication.class , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestScenarioForBankAccount {

	@Autowired
	private TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	
	private String getRootUrl() {
		return "http://localhost:" + port;
	}
	
	@Test
	public void ContextLoad() {
		
	}
	
	/**
	 * Below Test Scenario Will Create First Saving Account For Creation Of Current Account.
	 */
	@Test
	public void testCreateAccount() {
	CustomerRequest customerRequest = getCustomerRequest();
        ResponseEntity<CustomerRequest> postResponse = restTemplate.postForEntity(getRootUrl() + "/accounts/saving" , customerRequest, CustomerRequest.class);
        Assert.assertNotNull(postResponse);
        Assert.assertNotNull(postResponse.getBody());
	}
	
	/**
	 * Below Test Scenario Will Search If Saving Account Exists, If Yes Then Create New Account Also Transfer Amount If
	 * InitialCreadit is Non Zero.
	 */
	@Test
	public void testAddCurrentAccount() {
	testCreateAccount();
	Client searchAndCreateAccount = restTemplate.getForObject(getRootUrl() + "/accounts/current?custId=101&initialCredit=1000", Client.class);
	Assert.assertNotNull(searchAndCreateAccount);
	    
	}
	
	/**
	 * Below Test Scenario Will Result Customer Info of Current Account Which is Recently Created.
	 */
	@Test
	public void testGetCustomerInfoForCurrentAccount() {
	testAddCurrentAccount();
	Client client = restTemplate.getForObject(getRootUrl() + "/accounts/customerInfo?custId=101", Client.class);
	Assert.assertNotNull(client);
	Assert.assertNotNull(client.getAccounts());
	}
	
	@NotNull
	private CustomerRequest getCustomerRequest() {
	CustomerRequest customerRequest = new CustomerRequest();
	Client client = new Client();
	Account account = new Account();
	client.setId(1);
        client.setCustomerId(101);
        client.setFirstName("shivam");
        client.setLastName("Kesherwani");
        client.setAddress("Amstelveen");
        client.setBranch("Amsterdam");
        client.setEmailId("shivam.kesherwani@gmail.com");
        client.setIfscCode("ICSD00001");
        account.setAccountNumber("CAP089039458");
        account.setAccountType("saving");
        
        account.setBalance(10993);
        account.setCustomerId(101);
        account.setPid(903);
        client.setAccounts(Collections.singletonList(account));
        customerRequest.setClient(client);
	return customerRequest;
	}

}
