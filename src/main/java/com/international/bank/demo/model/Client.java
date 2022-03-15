package com.international.bank.demo.model;

import javax.persistence.*;
import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
public class Client {

    @Id
    private int id;

    private int customerId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String address;
    private String branch;
    private String ifscCode;

    @OneToMany(targetEntity = Account.class , cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "key" , referencedColumnName = "id")
    private List<Account> accounts;
}
