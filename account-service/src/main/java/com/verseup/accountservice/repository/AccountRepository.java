package com.verseup.accountservice.repository;

import com.verseup.accountservice.entity.Account;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface AccountRepository extends CassandraRepository<Account, String> {
}
