package com.jadz.fund.adapter.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.math.BigDecimal;

@Entity
@Table(name = "ACCOUNT", schema = "FUND_SCHEMA")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccountEntity {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "ACCOUNT_ID_SEQUENCE_GENERATOR", schema = "FUND_SCHEMA", sequenceName = "ACCOUNT_ID_SEQ",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_ID_SEQUENCE_GENERATOR")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "ACCOUNT_ID", nullable = false, unique = true)
    @NaturalId
    @EqualsAndHashCode.Include
    private Long accountId;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "BALANCE", precision = 38, scale = 19)
    private BigDecimal balance;
}
