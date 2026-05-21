DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS reservation_time;
DROP TABLE IF EXISTS theme;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS store;

CREATE TABLE member (
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    name     VARCHAR(20)  NOT NULL,
    login_id VARCHAR(50)  NOT NULL,
    password VARCHAR(255) NOT NULL,
    role       VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (login_id)
);

CREATE TABLE store (
    id      BIGINT      NOT NULL AUTO_INCREMENT,
    name    VARCHAR(50) NOT NULL,
    member_id     BIGINT    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE RESTRICT
);

CREATE TABLE theme (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    name          VARCHAR(20)  NOT NULL,
    description   VARCHAR(255) NOT NULL,
    thumbnail_url VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation_time (
    id       BIGINT NOT NULL AUTO_INCREMENT,
    start_at TIME   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation (
    id       BIGINT      NOT NULL AUTO_INCREMENT,
    member_id BIGINT     NOT NULL,
    date     DATE        NOT NULL,
    time_id  BIGINT      NOT NULL,
    theme_id BIGINT      NOT NULL,
    store_id BIGINT      NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES member (id) ON DELETE RESTRICT,
    FOREIGN KEY (time_id) REFERENCES reservation_time (id) ON DELETE RESTRICT,
    FOREIGN KEY (theme_id) REFERENCES theme (id) ON DELETE RESTRICT,
    FOREIGN KEY (store_id) REFERENCES store (id) ON DELETE RESTRICT
);