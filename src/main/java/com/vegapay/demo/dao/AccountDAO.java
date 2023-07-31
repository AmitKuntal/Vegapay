package com.vegapay.demo.dao;

import com.vegapay.demo.domain.Account;
import com.vegapay.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountDAO {

    @Autowired
    AccountRepository accountRepository;

    public List<Account> getAllAccount(){
        return accountRepository.findAll();
    }

    public Account getAccountByAccountId(String accountId){
        return accountRepository.getById(accountId);
    }

    public void createAccount(Account account){
        accountRepository.save(account);
    }
}
