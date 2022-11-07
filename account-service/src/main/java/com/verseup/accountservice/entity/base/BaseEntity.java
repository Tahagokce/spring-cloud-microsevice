package com.verseup.accountservice.entity.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class BaseEntity {
    @Id
    @PrimaryKey
    private String id = UUID.randomUUID().toString();

    private Date createdAt;

    @Column("is_active")
    private Boolean active;
}
