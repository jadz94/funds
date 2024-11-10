package com.jadz.fund.domain.serviceadapter

import com.jadz.fund.domain.exception.BadRequestException
import com.jadz.fund.domain.exception.NotFoundException
import com.jadz.fund.domain.exception.ResourceUpdateException
import com.jadz.fund.domain.model.Account
import com.jadz.fund.domain.port.AccountRepositoryPort
import spock.lang.Specification

class AccountServiceAdapterSpec extends Specification {

    AccountRepositoryPort accountRepositoryPort = Mock()
    AccountServiceAdapter accountServiceAdapter = new AccountServiceAdapter(accountRepositoryPort)


    def "save with valid param should call save on repository with correct param"() {
        given:
        Account account = buildAccount()

        when:
        accountServiceAdapter.save(account)

        then:
        1 * accountRepositoryPort.save(account)
    }

    def "getById with valid param and Account exits  should call getById on repository with correct param"() {
        given:
        def accountId = 1L
        when:
        accountServiceAdapter.getById(accountId)

        then:
        1 * accountRepositoryPort.getByAccountId(accountId) >> Optional.of(buildAccount())
        noExceptionThrown()
    }

    def "getAll should rely on repository findAll"() {
        when:
        accountServiceAdapter.getAll()

        then:
        1 * accountRepositoryPort.findAll()
        noExceptionThrown()
    }

    def "getById with valid param should And Account does not exist call getById on repository with correct param"() {
        given:
        def accountId = 1L
        when:
        accountServiceAdapter.getById(accountId)

        then:
        1 * accountRepositoryPort.getByAccountId(accountId) >> Optional.empty()
        thrown(NotFoundException)
    }

    def"given accountId when deposit should rely on repository deposit"(){
        given:
        def accountId = 1L
        when:
        accountServiceAdapter.deposit(accountId, BigDecimal.ONE)

        then:
        1 * accountRepositoryPort.deposit(accountId , BigDecimal.ONE) >> 1
        noExceptionThrown()
    }

    def"given accountId deposit should rely on repository deposit but error while updating"(){
        given:
        def accountId = 1L
        when:
        accountServiceAdapter.deposit(accountId, BigDecimal.ONE)

        then:
        1 * accountRepositoryPort.deposit(accountId , BigDecimal.ONE) >> 0
        thrown(ResourceUpdateException)
    }

    def"given accountId withdraw should rely on repository withdraw account exists but error when updating"(){
        given:
        def accountId = 1L
        Account account = buildAccount()
        when:
        accountServiceAdapter.withdraw(accountId, BigDecimal.ONE)

        then:
        1 * accountRepositoryPort.getByAccountId(accountId) >> Optional.of(account)
        1 * accountRepositoryPort.withdraw(accountId, BigDecimal.ONE) >> 0
        thrown(ResourceUpdateException)
    }

    def"given accountId withdraw should rely on repository withdraw account but account does not have sufficient funds"(){
        given:
        def accountId = 1L
        Account account = buildAccount()
        when:
        accountServiceAdapter.withdraw(accountId, BigDecimal.valueOf(20L))

        then:
        1 * accountRepositoryPort.getByAccountId(accountId) >> Optional.of(account)
        0 * accountRepositoryPort.withdraw(accountId, BigDecimal.valueOf(20L)) >> 0
        thrown(BadRequestException)
    }

    def"given accountId withdraw should rely on repository withdraw account"(){
        given:
        def accountId = 1L
        Account account = buildAccount()
        when:
        accountServiceAdapter.withdraw(accountId, BigDecimal.ONE)

        then:
        1 * accountRepositoryPort.getByAccountId(accountId) >> Optional.of(account)
        1 * accountRepositoryPort.withdraw(accountId, BigDecimal.ONE) >> 1
        noExceptionThrown()
    }


    static Account buildAccount() {
        Account.builder()
                .accountId(1)
                .currency("EUR")
                .balance(BigDecimal.TEN)
                .build()
    }

}
