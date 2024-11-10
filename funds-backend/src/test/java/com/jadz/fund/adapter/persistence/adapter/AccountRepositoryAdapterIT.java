package com.jadz.fund.adapter.persistence.adapter;

import com.jadz.fund.adapter.persistence.AbstractPostgreSQLTestContainerIT;
import com.jadz.fund.adapter.persistence.entity.AccountEntity;
import com.jadz.fund.adapter.persistence.mapper.EntityMapper;
import com.jadz.fund.adapter.persistence.mapper.EntityMapperImpl;
import com.jadz.fund.adapter.persistence.repository.AccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

class AccountRepositoryAdapterIT extends AbstractPostgreSQLTestContainerIT {

    AccountRepositoryAdapter accountRepositoryAdapter;

    @Autowired
    AccountRepository accountRepository;
    EntityMapper entityMapper = new EntityMapperImpl();

    @PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    public void init() {
        accountRepositoryAdapter = new AccountRepositoryAdapter(
                accountRepository,
                entityMapper);
        accountRepository.deleteAll();
        for (int i = 0; i < 10; i++) {
            final Long accountId = Long.parseLong(RandomStringUtils.randomNumeric(6));
            accountRepository.saveAndFlush(buildAccountEntity(accountId));
        }
    }

    @Test
    void given_existing_account_when_findByAccountID_expected_account_is_returned() {
        //GIVEN
        final var accountEntity = buildAccountEntity(1L);
        accountRepository.save(accountEntity);

        //WHEN
        var result = accountRepositoryAdapter.getByAccountId(1L);

        //THEN
        assertThat(result).isPresent();
        assertThat(result.get().getAccountId()).isEqualTo(1L);
        assertThat(result.get().getCurrency()).isEqualTo("EUR");
    }

    @Test
    void given_existing_account_when_findAll_expected_accounts_are_returned() {
        //WHEN
        var result = accountRepositoryAdapter.findAll();

        //THEN
        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(10);
    }

    @Test
    @Transactional
    void given_existing_account_when_withdraw_expected_amount_is_withdrawn_from_account() {
        //GIVEN
        final var accountEntity = buildAccountEntity(2L);
        accountRepository.save(accountEntity);

        //WHEN
        accountRepositoryAdapter.withdraw(2L, BigDecimal.ONE);
        entityManager.clear();

        final var result = accountRepositoryAdapter.getByAccountId(2L);

        //THEN
        assertThat(result).isPresent();
        assertThat(result.get().getAccountId()).isEqualTo(2L);
        assertThat(result.get().getBalance()).isEqualTo(BigDecimal.valueOf(9L));
    }

    @Test
    @Transactional
    void given_existing_account_when_deposit_expected_amount_is_deposited_from_account() {
        //GIVEN
        final var accountEntity = buildAccountEntity(2L);
        accountRepository.save(accountEntity);

        //WHEN
        accountRepositoryAdapter.deposit(2L, BigDecimal.TWO);
        entityManager.clear();

        final var result = accountRepositoryAdapter.getByAccountId(2L);

        //THEN
        assertThat(result).isPresent();
        assertThat(result.get().getAccountId()).isEqualTo(2L);
        assertThat(result.get().getBalance()).isEqualTo(BigDecimal.valueOf(12L));
    }

    private static AccountEntity buildAccountEntity(final Long id) {
        final var accountEntity = new AccountEntity();
        accountEntity.setAccountId(id);
        accountEntity.setCurrency("EUR");
        accountEntity.setBalance(BigDecimal.TEN);
        return accountEntity;
    }
}