package com.jadz.fund.domain.serviceadapter;

import com.jadz.fund.domain.exception.BadRequestException;
import com.jadz.fund.domain.exception.NotFoundException;
import com.jadz.fund.domain.exception.ResourceUpdateException;
import com.jadz.fund.domain.model.Account;
import com.jadz.fund.domain.port.AccountRepositoryPort;
import com.jadz.fund.domain.port.AccountServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceAdapter implements AccountServicePort {

    private final AccountRepositoryPort accountRepositoryPort;

    @Override
    public Account save(Account account) {
        return accountRepositoryPort.save(account);
    }

    @Override
    @Transactional(readOnly = true)
    public Account getById(Long accountId) {
        return accountRepositoryPort.getByAccountId(accountId)
                .orElseThrow(() -> new NotFoundException(String.format("Account with code %s not found", accountId)));
    }

    @Override
    public void deposit(Long accountId, BigDecimal amount) {
        int countDepositUpdate = accountRepositoryPort.deposit(accountId, amount);
        if (countDepositUpdate == 0) {
            throw new ResourceUpdateException(String.format("Account with code %s not found", accountId));
        }
    }

    @Override
    public void withdraw(Long accountId, BigDecimal amount) {
        final var account = getById(accountId);
        checkAmountToBeWithdrawn(account.getBalance(), amount);
        int countWithdrawUpdate = accountRepositoryPort.withdraw(accountId, amount);
        if (countWithdrawUpdate == 0) {
            throw new ResourceUpdateException(String.format("Account with code %s not found", accountId));
        }
    }

    private void checkAmountToBeWithdrawn(BigDecimal balance, BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new BadRequestException("Account does not have enough balance to withdraw from it");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAll() {
        return accountRepositoryPort.findAll();
    }


}
