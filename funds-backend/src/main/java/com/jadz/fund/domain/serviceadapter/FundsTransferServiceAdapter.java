package com.jadz.fund.domain.serviceadapter;

import com.jadz.fund.domain.port.AccountServicePort;
import com.jadz.fund.domain.port.FundTransferServicePort;
import com.jadz.fund.funds.model.FundTransferDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FundsTransferServiceAdapter implements FundTransferServicePort {

    private final AccountServicePort accountService;

    @Override
    public String processTransfer(final FundTransferDTO transfer) {
        accountService.withdraw(transfer.getFromAccount().getId(), transfer.getAmount());
        accountService.deposit(transfer.getToAccount().getId(), transfer.getAmount());
        return "Transfer processed successfully";
    }
}
