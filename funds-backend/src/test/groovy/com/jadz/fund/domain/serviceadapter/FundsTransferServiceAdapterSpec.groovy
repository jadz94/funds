package com.jadz.fund.domain.serviceadapter

import com.jadz.fund.domain.model.Account
import com.jadz.fund.domain.port.AccountRepositoryPort
import com.jadz.fund.domain.port.AccountServicePort
import com.jadz.fund.funds.model.AccountDTO
import com.jadz.fund.funds.model.AccountTransferDTO
import com.jadz.fund.funds.model.FundTransferDTO
import spock.lang.Specification

class FundsTransferServiceAdapterSpec extends Specification {
    AccountServicePort accountService = Mock()

    FundsTransferServiceAdapter serviceAdapter = new FundsTransferServiceAdapter(accountService)


    def "save with valid param should call save on repository with correct param"() {
        given:
        def transferDto = new FundTransferDTO()
                .fromAccount(new AccountTransferDTO().id(1L).currency("EUR"))
                .toAccount(new AccountTransferDTO().id(2L).currency("EUR"))
                .amount(BigDecimal.TEN)
        when:
        serviceAdapter.processTransfer(transferDto)

        then:
        1 * accountService.withdraw(1L, BigDecimal.TEN)
        1 * accountService.deposit(2L, BigDecimal.TEN)
    }
}
