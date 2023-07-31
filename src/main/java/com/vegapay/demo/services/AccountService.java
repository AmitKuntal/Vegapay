package com.vegapay.demo.services;

import com.vegapay.demo.dao.AccountDAO;
import com.vegapay.demo.domain.Account;
import com.vegapay.demo.dto.AccountDTO;
import com.vegapay.demo.dto.CreateAccountDTO;
import com.vegapay.demo.dto.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    AccountDAO accountDAO;


    public GenericResponse createAccount(CreateAccountDTO body) throws Exception {
        if(body.getCustomerId() ==null){
            throw new Exception("customer id can't be null");
        }else {
            Account account = new Account();
            account.setId(getUUID());
            account.setCustomerId(body.getCustomerId());
            account.setAccountLimit(0l);
            account.setPerTransactionLimit(0l);
            account.setLastAccountLimit(0l);
            account.setLastPerTransactionLimit(0l);
            account.setAccountLimitUpdateTime(getCurrentTimeStamp());
            account.setPerTransactionLimitUpdateTime(getCurrentTimeStamp());
            accountDAO.createAccount(account);
            GenericResponse response = new GenericResponse();
            response.setStatus("success");
            response.setMessage("Account created successfully");
            return response;
        }
    }

    public AccountDTO getAccountInfo(String accountId) throws Exception {
        Account account = accountDAO.getAccountByAccountId(accountId);
        if(account ==null){
            throw new Exception("Account not found");
        }else {
            AccountDTO response = new AccountDTO();
            response.setId(account.getId());
            response.setCustomerId(account.getCustomerId());
            response.setAccountLimit(account.getAccountLimit());
            response.setPerTransactionLimit(account.getPerTransactionLimit());
            response.setAccountLimitUpdateTime(account.getAccountLimitUpdateTime());
            response.setPerTransactionLimitUpdateTime(account.getPerTransactionLimitUpdateTime());
            response.setLastAccountLimit(account.getLastAccountLimit());
            response.setLastPerTransactionLimit(account.getLastPerTransactionLimit());
            return response;
        }
    }

    private String getUUID(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

    private Timestamp getCurrentTimeStamp(){
        java.util.Date currentDate =  new java.util.Date();
        return new Timestamp(currentDate.getTime());
    }
}
