package com.vegapay.demo.controller;

import com.vegapay.demo.dto.AccountDTO;
import com.vegapay.demo.dto.CreateAccountDTO;
import com.vegapay.demo.dto.GenericResponse;
import com.vegapay.demo.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping
    public Object createAccount(@RequestBody CreateAccountDTO body){
        try{
            return ResponseEntity.ok(accountService.createAccount(body));
        } catch (Exception exception){
            GenericResponse response = new GenericResponse();
            response.setStatus("error");
            response.setMessage(exception.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/{id}")
    public Object getAccount(@PathVariable("id") String accountId){
        try {
            AccountDTO accountDTO = accountService.getAccountInfo(accountId);
            return ResponseEntity.ok(accountDTO);
        } catch (Exception exception){
            GenericResponse response = new GenericResponse();
            response.setStatus("error");
            response.setMessage(exception.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

}
