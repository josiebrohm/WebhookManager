-- Active: 1755869732761@@127.0.0.1@5432@webhook_manager_db@core
-- Database: webhook_manager_db

-- DROP DATABASE IF EXISTS webhook_manager_db;

CREATE DATABASE webhook_manager_db
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'C'
    LC_CTYPE = 'C'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

-- SCHEMA: core

-- DROP SCHEMA IF EXISTS core ;

CREATE SCHEMA IF NOT EXISTS core
    AUTHORIZATION postgres;

-- Table: core.endpoints

-- DROP TABLE IF EXISTS core.endpoints;

CREATE TABLE IF NOT EXISTS core.endpoints
(
    id uuid NOT NULL,
    url character varying(255) COLLATE pg_catalog."default" NOT NULL,
    secret character varying(255) COLLATE pg_catalog."default" NOT NULL,
    enabled boolean NOT NULL,
    last_success_at timestamp without time zone,
    last_failure_at timestamp without time zone,
    CONSTRAINT endpoints_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS core.endpoints
    OWNER to postgres;

-- Table: core.publisher_accounts

-- DROP TABLE IF EXISTS core.publisher_accounts;

CREATE TABLE IF NOT EXISTS core.publisher_accounts
(
    id uuid NOT NULL,
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    api_key character varying(255) COLLATE pg_catalog."default" NOT NULL,
    rate_limit integer,
    CONSTRAINT publisher_accounts_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS core.publisher_accounts
    OWNER to postgres;

-- Table: core.send_attempts

-- DROP TABLE IF EXISTS core.send_attempts;

CREATE TABLE IF NOT EXISTS core.send_attempts
(
    id uuid NOT NULL,
    webhook_message_id uuid NOT NULL,
    status character varying COLLATE pg_catalog."default" NOT NULL,
    retry_count integer NOT NULL DEFAULT 0,
    max_retries integer NOT NULL,
    scheduled_for timestamp without time zone,
    created_at timestamp without time zone,
    delivered_at timestamp without time zone,
    failed_at timestamp without time zone,
    CONSTRAINT send_attempts_pkey PRIMARY KEY (id),
    CONSTRAINT webhook_message_id FOREIGN KEY (webhook_message_id)
        REFERENCES public.webhook_messages (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS core.send_attempts
    OWNER to postgres;

-- Table: core.webhook_messages

-- DROP TABLE IF EXISTS core.webhook_messages;

CREATE TABLE IF NOT EXISTS core.webhook_messages
(
    id uuid NOT NULL,
    endpoint_id uuid NOT NULL,
    publisher_account_id uuid NOT NULL,
    headers json NOT NULL,
    payload json NOT NULL,
    event_type character varying COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp without time zone NOT NULL,
    CONSTRAINT webhook_messages_pkey PRIMARY KEY (id),
    CONSTRAINT enpoint_id FOREIGN KEY (endpoint_id)
        REFERENCES public.endpoints (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT publisher_account_id FOREIGN KEY (publisher_account_id)
        REFERENCES public.publisher_accounts (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS core.webhook_messages
    OWNER to postgres;