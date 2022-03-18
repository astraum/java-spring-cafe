DROP TABLE IF EXISTS REPLY;
DROP TABLE IF EXISTS ARTICLE;
DROP TABLE IF EXISTS CAFE_USER;

CREATE TABLE ARTICLE
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY,
    timestamp     TIMESTAMP DEFAULT NOW(),
    writer_userid VARCHAR(255),
    writer_name   VARCHAR(255),
    title         VARCHAR(255),
    contents      VARCHAR(65535),
    deleted       BOOLEAN DEFAULT FALSE
);

CREATE TABLE REPLY
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY,
    article_id    BIGINT REFERENCES ARTICLE(id),
    timestamp     TIMESTAMP DEFAULT NOW(),
    writer_userid VARCHAR(255),
    writer_name   VARCHAR(255),
    contents      VARCHAR(65535),
    deleted       BOOLEAN DEFAULT FALSE
);

CREATE TABLE CAFE_USER
(
    userid   VARCHAR(255) NOT NULL PRIMARY KEY,
    password VARCHAR(255),
    name     VARCHAR(255),
    email    VARCHAR(255)
);
