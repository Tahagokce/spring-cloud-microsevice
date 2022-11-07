package com.verseup.accountservice.service;

import com.verseup.accountservice.entity.Account;
import com.verseup.accountservice.modal.GenericResponse;

import java.util.List;

public interface AccountService {
    GenericResponse<Account> get(String id);

    GenericResponse<Account> save(Account account);

    GenericResponse<Account> update(String id, Account account);

    GenericResponse<Account> delete(String id);

    GenericResponse<List<Account>> findAll();
}
