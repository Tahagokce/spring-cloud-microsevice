package com.verseup.accountservice.entity;

import com.verseup.accountservice.entity.base.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@ToString
@Table("accounts")
@EqualsAndHashCode(of = {"id"})
public class Account extends BaseEntity {
    private String username;
    private String email;
    private String password;
}
