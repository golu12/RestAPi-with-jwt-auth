package com.international.bank.demo.service;

import com.international.bank.demo.model.Client;

import java.util.Optional;

public interface AccountService {

    Optional<Client> addSavingsAccount(Client client);

    Optional<Client> searchAndCreateCurrentAccount(int custId , double initialCredit);

    Optional<Client> getCustomerInformationOfNewAccount(int custId);
}
