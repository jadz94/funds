-- database
CREATE DATABASE fund_db;
\c fund_db
-- schema
CREATE SCHEMA fund_schema;
-- user
CREATE USER fund_owner WITH PASSWORD 'admin123';
--privileges
GRANT ALL PRIVILEGES ON DATABASE fund_db TO fund_owner;
GRANT ALL PRIVILEGES ON SCHEMA fund_schema TO fund_owner;
