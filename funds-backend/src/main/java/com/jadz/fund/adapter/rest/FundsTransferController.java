package com.jadz.fund.adapter.rest;

import com.jadz.fund.domain.port.FundTransferServicePort;
import com.jadz.fund.funds.api.FundTransferApi;
import com.jadz.fund.funds.model.FundTransferDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class FundsTransferController implements FundTransferApi {

    private final FundTransferServicePort fundTransferService;

    @Override
    public ResponseEntity<String> transferFunds(FundTransferDTO fundTransferDTO) {
        return ResponseEntity.ok().body(fundTransferService.processTransfer(fundTransferDTO));
    }
}
