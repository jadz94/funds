package com.jadz.fund.adapter.rest;

import com.google.gson.Gson;
import com.jadz.fund.FundApplication;
import com.jadz.fund.adapter.rest.mapper.JsonMapper;
import com.jadz.fund.domain.port.AccountServicePort;
import com.jadz.fund.funds.model.AccountDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {FundApplication.class})
@ActiveProfiles("it")
class AccountControllerIT {

    @Autowired
    protected MockMvc mvc;
    @MockBean
    private AccountServicePort accountService;
    @Autowired
    private JsonMapper jsonMapper;


    @Test
    void given_AccountDTO_when_create_should_return_200()
            throws Exception {
        final var request = buildAccountDTO();
        final var model = jsonMapper.map(request);
        when(accountService.save(model)).thenReturn(model);

        mvc.perform(post("/accounts")
                        .contentType("application/vnd.fnd.account-api.v1+json")
                        .content(new Gson().toJson(request)))
                .andExpect(status().isOk());
    }

    @Test
    void given_ExistingAccounts_when_getAll_should_return_AllAccounts()
            throws Exception {
        final var request = buildAccountDTO();
        final var model = jsonMapper.map(request);
        when(accountService.getAll()).thenReturn(List.of(model, model));

        mvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/vnd.fnd.accounts-api.v1+json"));
    }

    private AccountDTO buildAccountDTO() {
        return new AccountDTO()
                .id(1L)
                .balance(BigDecimal.TEN)
                .currency("EUR");
    }
}