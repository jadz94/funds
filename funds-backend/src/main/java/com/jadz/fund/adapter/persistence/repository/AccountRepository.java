package com.jadz.fund.adapter.persistence.repository;

import com.jadz.fund.adapter.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long>,
        JpaSpecificationExecutor<AccountEntity> {
    Optional<AccountEntity> findByAccountId(Long accountId);

    @Modifying
    @Query("update AccountEntity set balance = balance + :amount where accountId = :accountId")
    int deposit(@Param("accountId") Long accountId, @Param("amount") BigDecimal amount);

    @Modifying
    @Query("update AccountEntity set balance = balance - :amount where accountId = :accountId")
    int withdraw(@Param("accountId") Long accountId, @Param("amount") BigDecimal amount);
}
