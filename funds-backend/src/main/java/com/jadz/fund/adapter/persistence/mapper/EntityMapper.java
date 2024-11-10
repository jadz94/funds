package com.jadz.fund.adapter.persistence.mapper;

import com.jadz.fund.adapter.persistence.entity.AccountEntity;
import com.jadz.fund.domain.model.Account;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;

@Mapper(componentModel = "spring", nullValueCheckStrategy = ALWAYS)
public interface EntityMapper {

    Account map(final AccountEntity entity);

    @Mapping(target = "id", ignore = true)
    AccountEntity map(final Account entity);

    @InheritConfiguration
    AccountEntity map(final Account account, final @MappingTarget AccountEntity entity);
}
