package com.jadz.fund.adapter.persistence.adapter;

import com.jadz.fund.adapter.persistence.entity.AccountEntity;
import com.jadz.fund.adapter.persistence.mapper.EntityMapper;
import com.jadz.fund.adapter.persistence.repository.AccountRepository;
import com.jadz.fund.domain.model.Account;
import com.jadz.fund.domain.port.AccountRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AccountRepositoryAdapter implements AccountRepositoryPort {
    private final AccountRepository accountRepository;
    private final EntityMapper entityMapper;

    @Override
    public Account save(Account account) {
        var optionalAccountEntity = accountRepository.findByAccountId(account.getAccountId());
        AccountEntity accountEntity;
        if (optionalAccountEntity.isPresent()) {
            accountEntity = entityMapper.map(account, optionalAccountEntity.get());
            log.debug("The account with code '{}' will be updated", accountEntity.getAccountId());
        } else {
            accountEntity = entityMapper.map(account);
            log.debug("The account with code '{}' will be created", accountEntity.getAccountId());
        }
        return entityMapper.map(accountRepository.save(accountEntity));
    }

    @Override
    public Optional<Account> getByAccountId(Long accountId) {
        return accountRepository.findByAccountId(accountId).map(entityMapper::map);
    }

    @Override
    public int deposit(Long accountId, BigDecimal amount) {
        return accountRepository.deposit(accountId, amount);
    }

    @Override
    public int withdraw(Long accountId, BigDecimal amount) {
        return accountRepository.withdraw(accountId, amount);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll().stream().map(entityMapper::map).toList();
    }
}
