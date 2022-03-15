package com.international.bank.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table
public class Account {

    @Id
    private int pid;

    private int customerId;

    private String accountType;

    private double balance;

    private String accountNumber;

}
