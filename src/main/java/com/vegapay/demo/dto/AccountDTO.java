package com.vegapay.demo.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AccountDTO {

    public String id;

    public String customerId;

    public Long accountLimit;

    public Long perTransactionLimit;

    public Long lastAccountLimit;

    public Long lastPerTransactionLimit;

    public Timestamp accountLimitUpdateTime;

    public Timestamp perTransactionLimitUpdateTime;


}
