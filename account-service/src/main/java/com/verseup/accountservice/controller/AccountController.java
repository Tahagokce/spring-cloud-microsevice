package com.verseup.accountservice.controller;

import com.verseup.accountservice.entity.Account;
import com.verseup.accountservice.modal.GenericResponse;
import com.verseup.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("{id}")
    public GenericResponse<Account> get(@PathVariable String id) {
        return accountService.get(id);
    }

    @PostMapping
    public GenericResponse<Account> save(Account account) {
        return accountService.save(account);
    }

    @PutMapping
    public GenericResponse<Account> update(String id, Account account) {
        return accountService.update(id, account);
    }

    @DeleteMapping
    public GenericResponse delete(String id) {
        return accountService.delete(id);
    }

    @GetMapping("all")
    public GenericResponse findAll(){
        return accountService.findAll();
    }
}
