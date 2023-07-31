package com.vegapay.demo.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "account_limit")
    private Long accountLimit;

    @Column(name = "per_transaction_limit")
    private Long perTransactionLimit;

    @Column(name = "last_account_limit")
    private Long lastAccountLimit;

    @Column(name ="last_per_transaction_limit")
    private Long lastPerTransactionLimit;

    @Column(name = "account_limit_update_time")
    private Timestamp accountLimitUpdateTime;

    @Column(name = "per_transaction_limit_update_time")
    private Timestamp perTransactionLimitUpdateTime;


}
