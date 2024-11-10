package com.jadz.fund.adapter.rest;

import com.google.gson.Gson;
import com.jadz.fund.FundApplication;
import com.jadz.fund.domain.port.FundTransferServicePort;
import com.jadz.fund.funds.model.AccountTransferDTO;
import com.jadz.fund.funds.model.FundTransferDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = {FundApplication.class})
@ActiveProfiles("it")
class FundsTransferControllerIT {

    @Autowired
    protected MockMvc mvc;
    @MockBean
    private FundTransferServicePort fundTransferServicePort;

    @Test
    void given_FundTransferDTO_when_transfer_should_return_200() throws Exception {
        final var request = buildFundTransferDto();
        when(fundTransferServicePort.processTransfer(request)).thenReturn("Transfer processed successfully");
        mvc.perform(post("/transfer")
                        .contentType("application/vnd.fnd.transfer-api.v1+json")
                        .content(new Gson().toJson(request)))
                .andExpect(status().isOk());
    }

    private static FundTransferDTO buildFundTransferDto() {
        return new FundTransferDTO()
                .fromAccount(new AccountTransferDTO().id(1L).currency("EUR"))
                .toAccount(new AccountTransferDTO().id(2L).currency("EUR"))
                .amount(BigDecimal.TEN);
    }


}