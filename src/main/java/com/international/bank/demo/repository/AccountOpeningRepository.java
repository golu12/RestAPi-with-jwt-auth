package com.international.bank.demo.repository;

import com.international.bank.demo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountOpeningRepository extends JpaRepository<Account, Integer> {
}
