package com.jadz.fund.domain.port;

import com.jadz.fund.domain.model.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountRepositoryPort {
    Account save(Account account);

    Optional<Account> getByAccountId(Long accountId);

    int deposit(Long accountId, BigDecimal amount);

    int withdraw(Long accountId, BigDecimal amount);

    List<Account> findAll();
}
