DROP TABLE IF EXISTS REPLY;
DROP TABLE IF EXISTS ARTICLE;
DROP TABLE IF EXISTS CAFE_USER;

CREATE TABLE CAFE_USER
(
    userid   VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255),
    name     VARCHAR(255) UNIQUE,
    email    VARCHAR(255) UNIQUE,
    PRIMARY KEY (userid)
);
CREATE TABLE ARTICLE
(
    id            BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
    timestamp     TIMESTAMP DEFAULT NOW(),
    writer_userid VARCHAR(255),
    writer_name   VARCHAR(255),
    title         VARCHAR(255),
    contents      TEXT(65535),
    deleted       BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (writer_userid) REFERENCES CAFE_USER(userid)
);

CREATE TABLE REPLY
(
    id            BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
    article_id    BIGINT NOT NULL,
    timestamp     TIMESTAMP DEFAULT NOW(),
    writer_userid VARCHAR(255),
    writer_name   VARCHAR(255),
    contents      TEXT(65535),
    deleted       BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (id),
    FOREIGN KEY (article_id) REFERENCES ARTICLE(id),
    FOREIGN KEY (writer_userid) REFERENCES CAFE_USER(userid)
);
