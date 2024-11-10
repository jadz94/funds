package com.jadz.fund.adapter.rest.mapper;

import com.jadz.fund.domain.model.Account;
import com.jadz.fund.funds.model.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = "spring", nullValueCheckStrategy = ALWAYS)
public interface JsonMapper {

    @Mapping(target = "accountId", source = "id")
    Account map(AccountDTO accountDTO);

    @Mapping(target = "id", source = "accountId")
    AccountDTO map(Account accountDTO);

    List<AccountDTO> map(List<Account> accounts);
}
