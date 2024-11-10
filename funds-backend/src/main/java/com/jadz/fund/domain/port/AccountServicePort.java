package com.jadz.fund.domain.port;

import com.jadz.fund.domain.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountServicePort {
  Account save(Account account);

  Account getById(Long accountId);

  List<Account> getAll();

  void deposit(Long accountId, BigDecimal amount);

  void withdraw(Long accountId, BigDecimal amount);
}

