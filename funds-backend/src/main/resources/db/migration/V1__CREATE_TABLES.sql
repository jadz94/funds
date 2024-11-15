CREATE TABLE IF NOT EXISTS FUND_SCHEMA.ACCOUNT
(
    ID               SERIAL PRIMARY KEY,
    ACCOUNT_ID       BIGINT NOT NULL,
    CURRENCY         VARCHAR(10),
    BALANCE           DECIMAL
);

ALTER TABLE FUND_SCHEMA.ACCOUNT
    ADD CONSTRAINT unique_account_id UNIQUE (ACCOUNT_ID);


CREATE TABLE IF NOT EXISTS FUND_SCHEMA.FUND_TRANSFER
(
    ID                  SERIAL PRIMARY KEY,
    FROM_ACCOUNT_ID     BIGINT NOT NULL,
    TO_ACCOUNT_ID       BIGINT NOT NULL,
    TRANSFER_DATE       TIMESTAMP NOT NULL,
    CURRENCY            VARCHAR(10),
    AMOUNT              DECIMAL
);
ALTER TABLE FUND_SCHEMA.FUND_TRANSFER ADD CONSTRAINT FK_FROM_ACCOUNT_ID FOREIGN KEY (FROM_ACCOUNT_ID)
    REFERENCES ACCOUNT (ID) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE FUND_SCHEMA.FUND_TRANSFER ADD CONSTRAINT FK_TO_ACCOUNT_ID FOREIGN KEY (TO_ACCOUNT_ID)
    REFERENCES ACCOUNT (ID) ON DELETE CASCADE ON UPDATE CASCADE;