package com.jadz.fund.domain.port;

import com.jadz.fund.funds.model.FundTransferDTO;

public interface FundTransferServicePort {

  String processTransfer(FundTransferDTO transfer);
}

