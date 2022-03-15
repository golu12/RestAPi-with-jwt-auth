package com.international.bank.demo.repository;

import com.international.bank.demo.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Query(value = "select  * from ACCOUNT INNER JOIN CLIENT  where ACCOUNT.CUSTOMER_ID= :custId" , nativeQuery = true)
    List<Client> getCustomerRecord(@Param("custId") int custId);

    @Query(value = "select * from ACCOUNT INNER JOIN CLIENT where ACCOUNT.ACCOUNT_TYPE= 'current' and ACCOUNT.CUSTOMER_ID= :custId", nativeQuery = true)
    Client getCustomerDetails(@Param("custId") int custId);
}
