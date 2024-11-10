package com.jadz.fund.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {
    @EqualsAndHashCode.Include
    private final Long accountId;
    private final String currency;
    private final BigDecimal balance;
}
