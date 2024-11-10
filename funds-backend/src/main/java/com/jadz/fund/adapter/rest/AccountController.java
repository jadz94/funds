package com.jadz.fund.adapter.rest;

import com.jadz.fund.adapter.rest.mapper.JsonMapper;
import com.jadz.fund.domain.port.AccountServicePort;
import com.jadz.fund.funds.api.AccountApi;
import com.jadz.fund.funds.model.AccountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountController implements AccountApi {

    private final AccountServicePort accountService;
    private final JsonMapper mapper;

    @Override
    public ResponseEntity<String> updateAccount(AccountDTO accountDTO) {
        accountService.save(mapper.map(accountDTO));
        return ResponseEntity.ok("Account successfully loaded");
    }

    @Override
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return ResponseEntity.ok(mapper.map(accountService.getAll()));
    }
}
