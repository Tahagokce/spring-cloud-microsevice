package com.verseup.accountservice.service.impl;

import com.verseup.accountservice.entity.Account;
import com.verseup.accountservice.exception.CustomException;
import com.verseup.accountservice.modal.GenericResponse;
import com.verseup.accountservice.modal.PageableData;
import com.verseup.accountservice.repository.AccountRepository;
import com.verseup.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public GenericResponse<Account> get(String id) {
        return GenericResponse.ok(accountRepository.findById(id).orElseThrow(() -> new CustomException("account.not.found")));
    }

    public GenericResponse<Account> save(Account account) {
        return GenericResponse.ok(accountRepository.save(account));
    }

    public GenericResponse<Account> update(String id, Account account) {
        Assert.isNull(id, "id cannot be null");
        return GenericResponse.ok(accountRepository.save(account));
    }

    public GenericResponse delete(String id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new CustomException("account.not.found"));
        accountRepository.delete(account);
        return GenericResponse.ok();
    }

    @Override
    public GenericResponse<List<Account>> findAll() {
        Pageable pageable = CassandraPageRequest.of(0, 6);
        Slice<Account> accountPage;
        List<Account> accounts = new ArrayList<>();

        do {
            accountPage = accountRepository.findAll(pageable);
            accountPage.getContent().forEach(account -> {
                log.info("id : {}", account.getId());
                accounts.add(account);

            });
            pageable = pageable.next();
        }
        while (!accountPage.isLast());
        return GenericResponse.fromPageableData(new PageableData<List<Account>>(pageable.getPageNumber(), pageable.getPageSize(), accountPage.stream().count(), accounts),accounts);
    }

}
