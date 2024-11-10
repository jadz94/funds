package com.jadz.fund.adapter.persistence.adapter

import com.jadz.fund.adapter.persistence.mapper.EntityMapper
import com.jadz.fund.adapter.persistence.mapper.EntityMapperImpl
import com.jadz.fund.adapter.persistence.repository.AccountRepository
import spock.lang.Specification

import static com.jadz.fund.domain.serviceadapter.AccountServiceAdapterSpec.buildAccount

class AccountRepositoryAdapterSpec extends Specification {

    AccountRepository accountRepository = Mock()
    EntityMapper entityMapper = new EntityMapperImpl()

    def accountRepositoryAdapter = new AccountRepositoryAdapter(accountRepository, entityMapper)

    def "save() a new account and return a expected account"() {
        given:
        def account = buildAccount()
        def accountEntity = entityMapper.map(account)
        accountRepository.findByAccountId(account.getAccountId()) >> Optional.empty()

        when:
        accountRepositoryAdapter.save(account)

        then:
        1 * accountRepository.save(accountEntity)
    }


    def "save() an existing account and return a expected account"() {
        given:
        def account = buildAccount()
        def accountEntity = entityMapper.map(account)
        accountRepository.findByAccountId(account.getAccountId()) >> Optional.of(accountEntity)

        when:
        accountRepositoryAdapter.save(account)

        then:
        1 * accountRepository.save(accountEntity)
    }
}
